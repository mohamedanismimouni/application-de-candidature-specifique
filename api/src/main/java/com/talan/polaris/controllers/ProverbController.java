package com.talan.polaris.controllers;

import com.talan.polaris.dto.ProverbDTO;
import com.talan.polaris.mapper.ProverbMapper;
import com.talan.polaris.services.ProverbService;
import com.talan.polaris.services.ScoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.talan.polaris.constants.CommonConstants.PROVERB_COMPONENT_LABEL;
import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;

/**
 * A controller defining proverb endpoints.
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */
@RestController
@RequestMapping(path = "${" + API_ENDPOINTS_RESOURCES_PREFIX + "}/proverb")
public class ProverbController {
    private final ScoreService scoreService;
    private final ProverbService proverbService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProverbController(ProverbService proverbService, ModelMapper modelMapper, ScoreService scoreService) {
        this.scoreService = scoreService;
        this.proverbService = proverbService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public Collection<ProverbDTO> getProverbs() {
        return ProverbMapper.convertProverbEntityListToDTO(this.proverbService.getProverbs(), modelMapper);
    }

    @PostMapping(path = "/addProverb")
    public ProverbDTO addProverb(@RequestBody ProverbDTO proverb) {
        return ProverbMapper.convertProverbEntityToDTO(this.proverbService.addProverb(ProverbMapper.convertProverbDTOToEntity(proverb, modelMapper)), modelMapper);
    }

    @GetMapping(path = "/publishedProverb")
    public ProverbDTO publishedProverb() {
        return ProverbMapper.convertProverbEntityToDTO(this.proverbService.getPublishedProverb(), modelMapper);
    }

    @GetMapping(path = "/scoreProverb")
    public Integer getProverbScore() {
        return scoreService.getScore(PROVERB_COMPONENT_LABEL);
    }

    @DeleteMapping(path = "/deleteProverb/{proverbId}")
    public void deleteFile(@PathVariable(value = "proverbId", required = true) Long proverbID) {
        proverbService.deleteProverb(proverbID);
    }
    @GetMapping(path = "/existProverbOfDay")
   public Boolean existProverOfDay()
       {
    return this.proverbService.existProverOfDay();
   }

}


