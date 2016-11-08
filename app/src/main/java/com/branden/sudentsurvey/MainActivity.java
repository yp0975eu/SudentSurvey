package com.branden.sudentsurvey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // A lot of this code has been adapted from
    // Android Programming Big Nerd Ranch Guide, 2nd Edition, GeoQuiz Tutorial

    private static final String TAG = "Main Activity";
    private boolean endOfSurvey;
    private Button mYesButton;
    private Button mNoButton;
    private TextView mQuestionTextView;
    private TextView mResultsTextView;
    private Button mNextPersonButton;
    private static final String KEY_INDEX = "index";
    private static final String KEY_VOTES_TOTAL = "votes_total";
    private static final String KEY_VOTES_YES = "votes_yes";
    private static final String KEY_VOTES_No = "votes_no";

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_football),
            new Question(R.string.question_animals),
            new Question(R.string.question_politics),
            new Question(R.string.question_breakfast),
    };
    private LinkedList<Question[]> voteTally = new LinkedList();

    // index of question we are on
    private int mCurrentIndex;

    // question object currently loaded
    private Question mCurrentQuestion;

    private void setupSurvey(){
        mCurrentIndex = 0;
        endOfSurvey = false;
        resetSurveyButtons();
    }

    // gets the current question and sets the view text
    private void setQuestionTextView(){

        // get the current question resource id
        int question = mCurrentQuestion.getQuestion();
        mQuestionTextView.setText(question);
    }

    private void incrementCounter(){
        // increment the counter
        mCurrentIndex++;
        Log.d(TAG, "Current Index" + mCurrentIndex);
    }

    private void setupResultsButtons(){
        // if end of survey and prepare buttons to show results
        mQuestionTextView.setText(R.string.end_of_survey);
        mYesButton.setText(R.string.button_show_results_text);
        mNoButton.setText(R.string.button_reset_text);
    }

    private void resetSurveyButtons(){
        // if end of survey and prepare buttons to show results
        mYesButton.setText(R.string.button_yes_text);
        mNoButton.setText(R.string.button_no_text);
    }

    // get the next question
    private void setNextQuestion(){
        mCurrentQuestion = mQuestionBank[mCurrentIndex];
    }

    private void tryNextQuestion() {

        // if we are within range then update the counter else
        // end the survey and add buttons to show and reset results
        if (mCurrentIndex < mQuestionBank.length - 1) {

            incrementCounter();
            setNextQuestion();
            setQuestionTextView();

        } else {

            endOfSurvey = true;
            Log.d(TAG, "End Of Survey");

            // add results to the vote tally
            voteTally.push(mQuestionBank);

            // show next button
            showNextPersonButton();

            // chang buttons to
            setupResultsButtons();
        }
    }

    // setup survey resets count to 0
    // set next question, loads the question from the questions queue
    // set question text view displays the actual quesiton text on the screen
    private void displayFirstQuestion(){
        setupSurvey();
        setNextQuestion();
        setQuestionTextView();
    }

    private void showResults(){

        int numberOfVotes = voteTally.size();

        // string for holding the question results
        String voteResultsString = "\n";

        for (Question question: mQuestionBank) {
            // getString takes resource id
            voteResultsString += getString(question.getQuestion()) +"\n"+ question.toString() + "\n";
        }
        // final string
        String voteCount = String.format("Number of votes: %d\n %s", numberOfVotes, voteResultsString);

        // set text
        mResultsTextView.setText(voteCount);

        // shet visiblity
        mResultsTextView.setVisibility(View.VISIBLE);

    }
    private void hideResults(){

        mResultsTextView.setVisibility(View.INVISIBLE);

    }

    private void resetQuestionObjectsToZero(){
        // Reset all question yes/no counts to 0,
        for (Question q: mQuestionBank) {
            q.resetVotes();
        }
    }

    private void showNextPersonButton(){
        // show button from: http://stackoverflow.com/a/6173541
        mNextPersonButton.setVisibility(View.VISIBLE);
    }

    private void hideNextPersonButton(){
        mNextPersonButton.setVisibility(View.INVISIBLE);
    }

    private void resetVoteTally(){
        voteTally.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to view objects
        mQuestionTextView = (TextView) findViewById(R.id.question_text);
        mYesButton = (Button) findViewById(R.id.answer_yes);
        mNoButton = (Button) findViewById(R.id.answer_no);
        mResultsTextView = (TextView) findViewById(R.id.results);
        mNextPersonButton = (Button) findViewById(R.id.next_person);


        mYesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ( !endOfSurvey) {

                    mCurrentQuestion.addVoteYes();

                    tryNextQuestion();

                } else {
                    // show results

                    showResults();

                }
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ( !endOfSurvey) {


                    mCurrentQuestion.addVoteNo();

                    tryNextQuestion();

                } else {
                    // reset survey
                    resetQuestionObjectsToZero();

                    resetVoteTally();

                    hideResults();

                    hideNextPersonButton();

                    // sets survey question index counter to 0 and displays first question
                    displayFirstQuestion();
                }

            }
        });

        // reset votes, reset buttons and hide button again
        mNextPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideResults();
                hideNextPersonButton();
                displayFirstQuestion();
            }
        });


            // sets survey counter to 0 and displays first question
        displayFirstQuestion();
    }
}
