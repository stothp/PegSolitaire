package controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Duration;
import java.time.ZonedDateTime;

@Entity
public class GameData {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private BoardType boardType;
    private Integer remainingMarbles;
    private ZonedDateTime startTime;
    private Duration duration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }

    public Integer getRemainingMarbles() {
        return remainingMarbles;
    }

    public void setRemainingMarbles(Integer remainingMarbles) {
        this.remainingMarbles = remainingMarbles;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}

