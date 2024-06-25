package com.talan.polaris.controllers;
import com.talan.polaris.dto.UsefulLinkDTO;
import com.talan.polaris.mapper.UsefulLinkMapper;
import com.talan.polaris.services.UsefulLinkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
/**
 * A controller defining Useful Link endpoints.
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/usefulLink")
public class UsefulLinkController {
    @Autowired
    UsefulLinkService usefulLinkService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping()
    public Collection<UsefulLinkDTO> getUsefulLinks() {
        return UsefulLinkMapper.convertUsefulLinkEntityListToDTO(this.usefulLinkService.findAllUsefulLink(), modelMapper);

    }

}


