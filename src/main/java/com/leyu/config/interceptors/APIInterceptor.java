package com.leyu.config.interceptors;

import com.leyu.config.Utils;
import com.leyu.config.annotation.Powers;
import com.leyu.config.aop.syslog.SyslogDao;
import com.leyu.dto.Result;
import com.leyu.pojo.User;
import com.leyu.utils.ApiSessionUtil;
import com.leyu.utils.PowerConsts;
import com.leyu.utils.ReqLimitUtils;
import com.leyu.utils.SessionUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class APIInterceptor implements HandlerInterceptor {
	public final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired private ApiSessionUtil apiSessionUtil;
	@Autowired private SyslogDao syslogDao;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception {

    	request.setAttribute("_t_start_time",System.currentTimeMillis());

    	int limitResult=ReqLimitUtils.residualReqNum("interceptor",new ReqLimitUtils.ReqLimit("yes","plat",1L,100,30*60L));//每秒请求超过100次后限制访问30分钟
		if(limitResult<=0){
			Utils.returnResult(new Result(Result.ERROR,"抱歉，您的请求过于频繁，请稍候再试!"));
			return false;
		}

    	boolean _need_login=hasNologin(handler);
		if(_need_login) return true;//有非登陆注解      直接通过

		User user=apiSessionUtil.getUser();
		if(user == null) {
			Utils.returnResult(new Result(Result.TIME_OUT,"登录超时或未登录，请登录！"));//为避免和代码中的300超时混淆，此处调整为3000为登录超时
			return false;
		}
		//token存在更新过期时间
		this.apiSessionUtil.updateExpire(this.apiSessionUtil.getTokenStr());
		Map<Integer, Object> userPower=new HashMap<>();
		boolean hasPower =hasPower(request,handler,userPower);
		if(!hasPower){
			Utils.returnResult(new Result(Result.NOPOWER,"抱歉，权限不足！"));
			return false;
		}
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
    	Long _t_start_time=(Long) request.getAttribute("_t_start_time");
		log.info(String.format("请求[%s]耗时[%s]ms",request.getRequestURI(),(System.currentTimeMillis()-_t_start_time)));
		String param=JSONObject.fromObject(request.getParameterMap()).toString();
		this.syslogDao.saveSysLog("用户请求",request.getRequestURI(),param,(System.currentTimeMillis()-_t_start_time));
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) throws Exception {
    }


	/**
	 * 是否需要登录
	 * @param handler
	 * @return	true 不需要，false 需要
	 */
	public static final boolean hasNologin(Object handler){
		if(handler == null)return false;
		if(!(handler instanceof HandlerMethod))return false;
		HandlerMethod m=(HandlerMethod) handler;
		Powers powers =m.getMethodAnnotation(Powers.class);
		if(powers == null) return false;
		PowerConsts[] powerConsts=powers.value();
		for (PowerConsts pc :powerConsts) {
			if(pc.getId()==PowerConsts.NOLOGINPOWER.getId())return true;
		}
		return false;
	}

	//	当前用户是否有权限操作此方法
	public static final boolean hasPower(HttpServletRequest request,Object handler){
		Map<Integer, Object> userPower = SessionUtil.getPower(request);
		return hasPower(request,handler,userPower);
	}
	//	当前用户是否有权限操作此方法
	public static final boolean hasPower(HttpServletRequest request,Object handler,Map<Integer, Object> userPower){
//		if(SessionUtil.isSuperAdmin())return true;//是超级管理员
		if(handler == null)return false;
		if(!(handler instanceof HandlerMethod))return false;
		HandlerMethod m=(HandlerMethod) handler;
		Powers powers =m.getMethodAnnotation(Powers.class);
		if(powers == null)return true;
		PowerConsts[] powerConsts=powers.value();
		Set<Integer> user_power_set=userPower.keySet();
		for (PowerConsts pc :powerConsts) {
			if(user_power_set.contains(pc.getId()))return true;
			if(pc.getId()==PowerConsts.NOLOGINPOWER.getId())return true;
			if(pc.getId()==PowerConsts.NOPOWER.getId())return true;//powerArray.length == 1
		}
		return false;
	}

}