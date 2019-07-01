package controller;

import constant.ApiConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import types.*;
import utils.*;


@RestController
public class InviteEventController extends BaseController {

    @PostMapping("/inviteEvent")
    public ResponseEntity<InviteEventResponse> handle(@RequestBody InviteEventRequest request) {
        logger.info("InviteEvent: " + request);

        // Step I: check parameters
        ExceptionUtils.assertPropertyValid(request.getSenderId(), ApiConstant.EVENT_SENDER_ID);
        ExceptionUtils.assertPropertyValid(request.getReceiverId(), ApiConstant.EVENT_RECEIVER_ID);
        ExceptionUtils.assertPropertyValid(request.getEvent(), ApiConstant.EVENT_EVENT);

        // Step II: check restriction (conflict, or naming rules etc.)
        Account sender = AccountUtils.getAccount(request.getSenderId(), ApiConstant.EVENT_SENDER_ID);
        Account receiver = AccountUtils.getAccount(request.getReceiverId(), ApiConstant.EVENT_RECEIVER_ID);
        ExceptionUtils.assertFriendship(sender, receiver.getAccountId());

        CreateEventRequest ER = request.getEvent();
        if(!request.getSenderId().equals(ER.getStarterId())){
            ExceptionUtils.invalidProperty("sendId need equal to starterId to invite");
        }

        // Note: reply's receiver and sender is opposite way of invitation
        String messageId = EventMessageUtils.createEventMessageToDatabase(
                null,
                ER.getEventname(),
                ER.getStarterId(),
                ER.getType(),
                ER.getStart(),
                ER.getCount(),
                ER.getDate(),
                ER.getLocation(),
                ER.getRepeat(),
                ER.getState(),
                ER.getDescription())
                .getMessageId();

        MessageQueueUtils.notifyAccounts(sender, receiver, MessageType.EVENT, messageId);

        // Step IV: create response object
        return new ResponseEntity<>(new InviteEventResponse().withMessageId(messageId),
                HttpStatus.CREATED);
    }
}
