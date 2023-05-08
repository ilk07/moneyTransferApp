package cp.moneytransferapp.controller;

import cp.moneytransferapp.dto.cardtocard.CardToCardMoneyTransferDTO;
import cp.moneytransferapp.dto.cardtocard.TransferConfirmationDTO;
import cp.moneytransferapp.dto.cardtocard.impl.DefaultCardToCardMoneyTransferBuilder;
import cp.moneytransferapp.entities.CardToCardMoneyTransfer;
import cp.moneytransferapp.entities.TransferConfirmation;
import cp.moneytransferapp.entities.TransferOperationId;
import cp.moneytransferapp.service.impl.DefaultTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/")
@CrossOrigin()
@RequiredArgsConstructor()
public class DefaultTransferController {
    private final DefaultCardToCardMoneyTransferBuilder transferDTOBuilder;
    private final DefaultTransferService service;

    @PostMapping(value = "transfer", consumes = "application/json", produces = "application/json")
    public TransferOperationId cardToCardMoneyTransfer(@Valid @RequestBody CardToCardMoneyTransferDTO cardToCardMoneyTransferDTO) {
        CardToCardMoneyTransfer cardToCardMoneyTransfer = transferDTOBuilder.cardToCardMoneyTransferFromDTO(cardToCardMoneyTransferDTO);
        return transferDTOBuilder.transferOperationIdFromCardToCardMoneyTransfer(service.transferRequest(cardToCardMoneyTransfer));
    }

    @PostMapping(value = "confirmOperation", consumes = "application/json", produces = "application/json")
    public TransferOperationId confirmTransferOperation(@Valid @RequestBody TransferConfirmationDTO transferConfirmationDto) {
        TransferConfirmation transferConfirmation = transferDTOBuilder.transferConfirmationFromDTO(transferConfirmationDto);
        CardToCardMoneyTransfer cardToCardMoneyTransfer = service.confirmTransferOperation(transferConfirmation);

        return transferDTOBuilder.transferOperationIdFromCardToCardMoneyTransfer(cardToCardMoneyTransfer);
    }
}
