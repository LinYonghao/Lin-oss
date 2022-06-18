package com.linyonghao.oss.manager.utils;

import com.linyonghao.oss.manager.entity.ResponseMessage;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import reactor.util.annotation.Nullable;

import java.util.Map;

public class ResponseUtil {
    public static ModelAndView view(String viewName, @Nullable Map<String, ?> model) {
        return new ModelAndView(viewName, model);
    }

    public static ModelAndView success(String viewName, @Nullable Map<String, ?> model, @Nullable String alertText) {
        if (alertText == null) {
            alertText = "";
        }
        ModelAndView modelAndView;
        if (viewName.startsWith("forward:")) {
            String view = viewName.substring("forward:".length());
            modelAndView = new ModelAndView(new RedirectView(view), model);
        }else{
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
        if (viewName.startsWith("forward:")) {
            String view = viewName.substring("forward:".length());
            modelAndView = new ModelAndView(new RedirectView(view), model);
            System.out.println(modelAndView);
            System.out.println(view);

        }else{
            modelAndView = new ModelAndView(viewName, model);
        }

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(ResponseMessage.CODE_ERROR);
        responseMessage.setMessage(alertText);
        modelAndView.addObject("responseMsg", responseMessage);
        return modelAndView;
    }


}
