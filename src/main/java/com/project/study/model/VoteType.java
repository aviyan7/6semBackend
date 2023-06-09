package com.project.study.model;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    private final int direction;

    VoteType(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public static VoteType fromDirection(int direction) {
        for (VoteType voteType : values()) {
            if (voteType.getDirection() == direction) {
                return voteType;
            }
        }
        throw new IllegalArgumentException("Vote type not found for direction: " + direction);
    }
}
