package bbgame;

/**
 * Created by south10 on 2017-06-07.
 */
class Game {
    private String number;
    private GameNumberGenerator gameNumberGenerator;

    public void setGameNumberGenerator(GameNumberGenerator gameNumberGenerator) {
        this.gameNumberGenerator = gameNumberGenerator;
    }

    public void generateNumber() {
        this.number = gameNumberGenerator.generate();
    }

    public GuessResult guess(String guessNum) {
        assertGuessNumberValid(guessNum);
        if (solved(guessNum))
            return createSolvedResult(true, 3, 0);
        else {
            return createNonSolvedResult(guessNum);
        }
    }

    private void assertGuessNumberValid(String guessNum) {
        if (guessNum == null) throw new IllegalArgumentException();
        if (guessNum.length() != 3) throw new IllegalArgumentException();
        for (char ch : guessNum.toCharArray())
            if (ch < '0' || ch > '9')
                throw new IllegalArgumentException();
        if (guessNum.charAt(0) == guessNum.charAt(1) || guessNum.charAt(0) == guessNum.charAt(2)
                || guessNum.charAt(1) == guessNum.charAt(2))
            throw new IllegalArgumentException();
    }

    private boolean solved(String guessNum) {
        return number.equals(guessNum);
    }

    private GuessResult createSolvedResult(boolean solved, int strikes, int balls) {
        return new GuessResult(solved, strikes, balls);
    }

    private GuessResult createNonSolvedResult(String guessNum) {
        int strikes = 0;
        int balls = 0;
        for (int i = 0; i < number.length(); i++) {
            int idx = number.indexOf(guessNum.charAt(i));
            if (idx == i) strikes++;
            else if (idx > -1) balls++;
        }
        return new GuessResult(false, strikes, balls);
    }
}
