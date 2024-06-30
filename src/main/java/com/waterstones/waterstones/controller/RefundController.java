package com.waterstones.waterstones.controller;

import com.waterstones.waterstones.dto.RefundRequestDTO;
import com.waterstones.waterstones.dto.RefundResponseDTO;
import com.waterstones.waterstones.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/refunds")
public class RefundController {

    @Autowired
    private RefundService refundService;

    @PostMapping
    @ResponseBody
    public RefundResponseDTO processRefund(@RequestBody RefundRequestDTO refundRequest) {
        return refundService.processRefund(refundRequest);
    }
}
