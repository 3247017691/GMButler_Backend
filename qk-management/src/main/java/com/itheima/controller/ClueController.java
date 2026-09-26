package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.dto.ClueDTO;
import com.itheima.dto.ClueFalseDTO;
import com.itheima.dto.ClueFollowDTO;
import com.itheima.entity.Clue;
import com.itheima.entity.PageResult;
import com.itheima.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clues")
public class ClueController {

    private final ClueService clueService;

    @Autowired
    public ClueController(ClueService clueService) {
        this.clueService = clueService;
    }

    /**
     * 线索列表查询(分页 + 条件)
     *
     * @param clueDTO
     * @return
     */
    @GetMapping
    public Result getClues(ClueDTO clueDTO) {
        PageResult<Clue> result = clueService.findByPageAndCondition(clueDTO);
        return Result.success(result);
    }

    /**
     * 线索池列表查询(分页 + 条件)
     *
     * @param clueDTO
     * @return
     */
    @GetMapping("/pool")
    public Result getCluePool(ClueDTO clueDTO) {
        PageResult<Clue> result = clueService.findPoolByPageAndCondition(clueDTO);
        return Result.success(result);
    }

    /**
     * 根据id获取线索详情(含跟进记录)
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getClue(@PathVariable Integer id) {
        Clue clue = clueService.getClueDetail(id);
        return Result.success(clue);
    }

    /**
     * 新增线索（仅管理员）
     *
     * @param clue
     * @return
     */
    @PostMapping
    public Result saveClue(@RequestBody Clue clue) {
        clueService.addClue(clue);
        return Result.success();
    }

    /**
     * 跟进线索
     *
     * @param clueFollowDTO
     * @return
     */
    @PutMapping
    public Result followClue(@RequestBody ClueFollowDTO clueFollowDTO) {
        clueService.follow(clueFollowDTO);
        return Result.success();
    }

    /**
     * 分配线索
     *
     * @param clueId
     * @param userId
     * @return
     */
    @PutMapping("/assign/{clueId}/{userId}")
    public Result assignClue(@PathVariable Integer clueId, @PathVariable Integer userId) {
        clueService.assign(clueId, userId);
        return Result.success();
    }

    /**
     * 伪线索处理
     *
     * @param id
     * @param clueFalseDTO
     * @return
     */
    @PutMapping("/false/{id}")
    public Result falseClue(@PathVariable Integer id, @RequestBody ClueFalseDTO clueFalseDTO) {
        clueService.markFalse(id, clueFalseDTO);
        return Result.success();
    }

    /**
     * 转商机处理
     *
     * @param id
     * @return
     */
    @PutMapping("/toBusiness/{id}")
    public Result toBusinessClue(@PathVariable Integer id) {
        clueService.toBusiness(id);
        return Result.success();
    }

    /**
     * 删除线索（仅管理员）
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteClue(@PathVariable Integer id) {
        clueService.deleteClue(id);
        return Result.success();
    }
}
