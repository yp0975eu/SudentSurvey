package com.branden.sudentsurvey;

/**
 * Created by badams on 11/7/16.
 */

public class Question {
    private int mQuestionResourceId;
    private int mVotesYes;
    private int mVotesNo;

    public Question(int questionResourceId) {
        mQuestionResourceId = questionResourceId;
        mVotesYes = 0;
        mVotesNo = 0;
    }

    public int getQuestion() {
        return mQuestionResourceId;
    }

    public void setQuestion(int question) {
        mQuestionResourceId = question;
    }

    public int getVotesYes() {
        return mVotesYes;
    }

    public void addVoteYes() {
        mVotesYes += 1;
    }

    public int getVotesNo() {
        return mVotesNo;
    }

    public void addVoteNo() {
        mVotesNo += 1;
    }
    public void resetVotes(){
        mVotesNo = 0;
        mVotesYes = 0;
    }

    public String toString(){
        return String.format("Yes: %s\t\tNo: %s",mVotesYes, mVotesNo);
    }
}
