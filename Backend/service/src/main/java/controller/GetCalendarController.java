package controller;

import constant.ApiConstant;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import types.Calendar;
import types.GetCalendarRequest;
import types.GetCalendarResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import store.DataStore;

@RestController
public class GetCalendarController extends BaseController{
    @PostMapping("/getCalendar")
    public ResponseEntity<GetCalendarResponse> handle(@RequestBody GetCalendarRequest request){
        logger.info("GetCalendar: " + request);

        if (request.getAccountId() == null || request.getAccountId().isEmpty()) {
            logger.error("Invalid AccountId!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Document document = new Document();
        document.put(ApiConstant.ACCOUNT_ACCOUNT_ID, request.getAccountId());
        Calendar calendar = dataStore.findOneInCollection(document, DataStore.COLLECTION_CALENDAR);
        if (calendar  == null) {
            logger.error("Account Id Not Found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GetCalendarResponse()
                .withAccountId(calendar.getAccountId())
                .withEvents(calendar.getEvents()),
                HttpStatus.OK);
    }
}
