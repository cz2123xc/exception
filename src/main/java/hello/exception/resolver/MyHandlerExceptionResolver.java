package hello.exception.resolver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if(ex instanceof IllegalArgumentException){
                log.info("서버 내부에서 어떤 이유에서든 IllegalArgumentException 오류가 발생하면 400 오류 (SC_BAD_REQUEST) 로 바꾼다");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); // response.sendError 를 하게 되면 was 는 오류 페이지를 찾는다.
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        return null; // 이렇게 하면 예외가 발생하더라도 예외 처리가 안되는 것을 방지한다.
    }
}
