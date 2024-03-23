package acs.b3o.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WebController {

    // 루트 경로("/")에 대한 요청을 처리하는 메소드
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok(new MessageResponse("WAS Server 확인"));
    }

    // 토큰 유효 확인에 대한 응답
    @GetMapping("/verify")
    public ResponseEntity<?> responseVerifyToken() {
        return ResponseEntity.ok("토큰이 유효합니다.");
    }

    // 간단한 응답 메시지를 담을 클래스
    private static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        // getter and setter
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}