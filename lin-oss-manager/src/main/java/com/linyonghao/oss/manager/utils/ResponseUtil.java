package com.linyonghao.oss.manager.utils;

import com.linyonghao.oss.manager.entity.ResponseMessage;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import reactor.util.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static ModelAndView view(String viewName, @Nullable Map<String, ?> model) {
        return new ModelAndView(viewName, model);
    }

    public static ModelAndView view(String viewName, HttpServletRequest request, @Nullable Map<String, Object> model) {
        Object lastModel = request.getSession().getAttribute("lastModel");
        request.getSession().removeAttribute("lastModel");
        Map<String, Object> lastModelMap = (Map<String, Object>) lastModel;
        if (model != null) {
            lastModelMap.putAll(model);
        }
        return new ModelAndView(viewName, lastModelMap);
    }

    public static ModelAndView success(String viewName, @Nullable Map<String, Object> model, @Nullable String alertText) {
        if (alertText == null) {
            alertText = "";
        }
        ModelAndView modelAndView;
        if (viewName.startsWith("forward:")) {
            String view = viewName.substring("forward:".length());
            modelAndView = new ModelAndView(new RedirectView(view), model);
        } else {
            modelAndView = new ModelAndView(viewName, model);
        }
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseMessage.CODE_SUCCESS);
        responseMessage.setMessage(alertText);
        modelAndView.addObject("responseMsg", responseMessage);
        return modelAndView;
    }

    public static ModelAndView error(String viewName, @Nullable Map<String, ?> model, @Nullable String alertText) {
        if (alertText == null) {
            alertText = "";
        }
        ModelAndView modelAndView;
        boolean isRedirect = false;
        if (viewName.startsWith("redirect:")) {
            String view = viewName.substring("redirect:".length());
            modelAndView = new ModelAndView(new RedirectView(view), model);
            isRedirect = true;
        } else {
            modelAndView = new ModelAndView(viewName, model);
        }

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseMessage.CODE_ERROR);
        responseMessage.setMessage(alertText);
        if (isRedirect) {

        } else {
            modelAndView.addObject("responseMsg", responseMessage);
        }
        return modelAndView;
    }

    /**
     * 利用Session来传model的值,解决重定向ModelAndView不继承问题
     *
     * @param viewName
     * @param request
     * @param model
     * @param alertText
     * @return
     */
    public static ModelAndView error(String viewName, HttpServletRequest request, @Nullable Map<String, Object> model, @Nullable String alertText) {
        if (model == null) {
            model = new HashMap<>();
        }
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseMessage.CODE_ERROR);
        responseMessage.setMessage(alertText);
        model.put("responseMsg", responseMessage);

        request.getSession().setAttribute("lastModel", model);
        return error(viewName, null, null);
    }

    public static ModelAndView success(String viewName, HttpServletRequest request, @Nullable Map<String, Object> model, @Nullable String alertText) {
        if (model == null) {
            model = new HashMap<>();
        }
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseMessage.CODE_ERROR);
        responseMessage.setMessage(alertText);
        model.put("responseMsg", responseMessage);

        request.getSession().setAttribute("lastModel", model);
        return success(viewName, null, null);
    }


}
