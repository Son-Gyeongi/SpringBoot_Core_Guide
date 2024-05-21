package com.springboot.actuator.config.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 커스텀 엔드포인트 생성
 * @Endpoint 어노테이션으로 빈에 추가된 객체들은 @ReadOperation, @WriteOperation, @DeleteOperation 어노테이션을
 * 사용해 JMX나 HTTP 를 통해 커스텀 엔드포인트를 노출시킬 수 있다.
 *
 * 간단하게 애플리케이션에 메모 기록을 남길 수 있는 기능을 엔드포인트로 생성
 */
@Component
@Endpoint(id = "note")
public class NoteEndpoint {

    private Map<String, Object> noteContent = new HashMap<>();

    @ReadOperation
    public Map<String, Object> getNote() {
        return noteContent;
    }

    @WriteOperation
    public Map<String, Object> writeNote(String key, Object value) {
        noteContent.put(key, value);
        return noteContent;
    }

    @DeleteOperation
    public Map<String, Object> deleteNote(String key) {
        noteContent.remove(key);
        return noteContent;
    }
}
