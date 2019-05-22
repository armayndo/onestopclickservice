package com.osc.server.service;

import com.osc.common.Common;
import com.osc.exception.RequestValidationException;
import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.*;
import com.osc.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Kerisnarendra on 17/05/2019.
 */

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherService extends BaseService<Voucher> {

}
