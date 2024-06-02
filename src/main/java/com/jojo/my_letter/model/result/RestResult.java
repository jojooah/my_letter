package com.jojo.my_letter.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RestResult {
    //저는 보통 리턴 클래스에 제네릭 타입으로 data를 선언하는데요. 맵으로 선언하니 여러가지
    //데이터를 넣을 수 있다는 장점은 있지만 key가 스트링 타입으로 들어가기 때문에
    //유지보수시에 어려움이 있을것으로 생각이 됩니다. 에러메시지도 enum타입으로 하나씩
    // 정리해보았는데, 저희 회사에서는 에러메시지를 넣을때
    //"저장되었습니다" 처럼 컨트롤러에서 바로 문자열을 넣는 편입니다.
    //데이터나 에러메시지를 제네릭,enum으로 정리하는것과
    // 스트링으로 그떄그떄 넣는것의 장단점이 궁금하고 현업에서는 어떤식으로 많이 쓰이는지 궁금합니다!

  //  private T data;
   private Map<String, Object> data;
    private String message;
    private RestError restError;

    public RestResult() {

    }
}
