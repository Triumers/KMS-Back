package org.triumers.kmsback.post.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.post.command.Application.dto.*;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;
import org.triumers.kmsback.post.command.Application.service.CmdTabService;

@RestController
@RequestMapping("/tab")
public class CmdTabController {

    private final CmdTabService cmdTabService;

    public CmdTabController(CmdTabService cmdTabService) {
        this.cmdTabService = cmdTabService;
    }

    @PostMapping("/regist/employee")
    public ResponseEntity<CmdJoinEmployeeDTO> registEmployeeTab(@RequestBody CmdJoinEmployeeDTO joinEmployeeDTO){

        if(joinEmployeeDTO.getEmployeeId() == null || joinEmployeeDTO.getTabId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdJoinEmployeeDTO joinEmployee = cmdTabService.addEmployeeTab(joinEmployeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(joinEmployee);
    }

    @DeleteMapping("/delete/employee")
    public ResponseEntity<CmdJoinEmployeeDTO> deleteEmployeeTab(@RequestBody CmdJoinEmployeeDTO joinEmployeeDTO){

        CmdJoinEmployeeDTO deletedEmployee = cmdTabService.deleteEmployeeTab(joinEmployeeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(deletedEmployee);
    }

    @PostMapping("/regist")
    public ResponseEntity<CmdTabRelationDTO> registTab(@RequestBody CmdTabRelationDTO tabRelationDTO, Integer userId){

        if(tabRelationDTO.getTopTab() == null || userId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CmdTabRelationDTO registTab = cmdTabService.registTab(tabRelationDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registTab);
    }
}
