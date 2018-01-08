package pl.wroc.uni.ift.android.quizactivity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jpola on 26.07.17.
 */

// Parcelable - daje możliwość przesłania całej klasy do Bundle
public class Question implements Parcelable {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean wasCheated;
    private boolean mIsAnswered;
    public boolean getWasCheated() {
        return wasCheated;
    }

    public void setWasCheated(boolean wasCheated) {
        this.wasCheated = wasCheated;
    }

    public Question(int textResId, boolean answerTrue)    {

        mTextResId=textResId;
        mAnswerTrue = answerTrue;
        wasCheated = false;
        mIsAnswered = false;

    }

    //our mIsAnswered getter and setter
    //getter
    public boolean getIsAnswered(){
        return mIsAnswered;
    }
    //setter
    public void setIsAnswered(boolean value){
        mIsAnswered = value;
    }

    protected Question(Parcel in) {
        mTextResId = in.readInt();
        mAnswerTrue = in.readByte() != 0;
        wasCheated = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTextResId);
        dest.writeByte((byte) (mAnswerTrue ? 1 : 0));
        dest.writeByte((byte) (wasCheated ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
