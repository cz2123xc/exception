package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class APIExceptionV2Controller {


    @ResponseStatus(HttpStatus.BAD_REQUEST) // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler]", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler // UserException
    public ResponseEntity<ErrorResult> userHandler(UserException e){
        log.error("[exceptionHandler]", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler // 나머지 Exception
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler]", e);
        return new ErrorResult("EX", "내부 오류");
    }


    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if(id.equals("ex")){
            throw new RuntimeException("예외 발생!");
        }
        if(id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if(id.equals("user-ex")){
            throw new UserException("사용자 예외 발생!");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}