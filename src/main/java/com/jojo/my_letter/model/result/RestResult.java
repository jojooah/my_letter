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
    //데이터나       에러메시지를 제네릭,enum으로 정리하는것과
    // 스트링으로 그떄그떄 넣는것의 장단점이 궁금하고 현업에서는 어떤식으로 많이 쓰이는지 궁금합니다!

 //답변입니다.
 // 1. 저는 제네릭보다는 Map<String, Object>을 사용하는 것이 더 좋다고 생각합니다.
 //   제네릭을 사용하면 데이터를 넣을 때마다 타입을 지정해주어야 하는데, Map을 사용하면 키와 값을 넣어주기만 하면 되기 때문입니다.
 //
 // 2. Key가 스트링일때 유지보수에 어떤 어려움이 있을까요?
 //
 // 3. 에러 메시지는 enum으로 정리하는 것이 좋은데 잘 정리해주신 것 같네요.
 // 에러 메시지를 컨트롤러에서 바로 문자열을 넣는 것은 좋지 않을 것 같아요. 메시지가 변경되었을 때, 모든 컨트롤러에서 변경해주어야 하기 때문인데요.
 // 이럴때는 message.properties 파일을 만들어서 에러 메시지를 관리하는 것이 좋습니다.

  //  private T data;
   private Map<String, Object> data;
    private String message;
    private RestErrorCode restError;

    public RestResult() {

    }
}
