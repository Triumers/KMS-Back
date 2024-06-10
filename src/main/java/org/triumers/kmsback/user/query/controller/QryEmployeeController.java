package org.triumers.kmsback.user.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.user.query.aggregate.vo.QryRequestEmployeeVO;
import org.triumers.kmsback.user.query.aggregate.vo.QryResponseEmployeeVO;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.user.query.service.QryEmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee/find")
public class QryEmployeeController {

    private final QryEmployeeService qryEmployeeService;

    @Autowired
    public QryEmployeeController(QryEmployeeService qryEmployeeService) {
        this.qryEmployeeService = qryEmployeeService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<QryResponseEmployeeVO> findById(@PathVariable int id) {

        try {
            QryEmployeeDTO employee = qryEmployeeService.findEmployeeById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseEmployeeVO("조회 성공", List.of(employee)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new QryResponseEmployeeVO("조회 실패", null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<QryResponseEmployeeVO> findAll() {

        try {
            List<QryEmployeeDTO> result = qryEmployeeService.findAllEmployee();
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseEmployeeVO("조회 성공", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new QryResponseEmployeeVO("조회 실패", null));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<QryResponseEmployeeVO> findByEmail(@PathVariable String email) {

        try {
            QryEmployeeDTO employee = qryEmployeeService.findEmployeeByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseEmployeeVO("조회 성공", List.of(employee)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new QryResponseEmployeeVO("조회 실패", null));
        }
    }

    @PostMapping("/name")
    public ResponseEntity<QryResponseEmployeeVO> createEmployee(@RequestBody QryRequestEmployeeVO request) {

        try {
            List<QryEmployeeDTO> result = qryEmployeeService.findEmployeeByName(request.getName());
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseEmployeeVO("조회 성공", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new QryResponseEmployeeVO("조회 실패", null));
        }
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<QryResponseEmployeeVO> findByTeamId(@PathVariable int teamId) {

        try {
            List<QryEmployeeDTO> result = qryEmployeeService.findEmployeeByTeamId(teamId);
            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseEmployeeVO("조회 성공", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new QryResponseEmployeeVO("조회 실패", null));
        }
    }
}
