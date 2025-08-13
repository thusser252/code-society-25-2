import {
  AnswerChoice,
  MultipleChoiceQuizQuestion,
  QuizQuestion,
  QuizQuestionProvider,
} from 'codedifferently-instructional';

export class TrishtanHusserQuiz implements QuizQuestionProvider {
  getProviderName(): string {
    return 'trishtanhusser';
  }

  makeQuizQuestions(): QuizQuestion[] {
    return [
      TrishtanHusserQuiz.makeQuestion0(),
      TrishtanHusserQuiz.makeQuestion1(),
      TrishtanHusserQuiz.makeQuestion2(),
    ];
  }

  private static makeQuestion0(): QuizQuestion {
    return new MultipleChoiceQuizQuestion(
      0,
      'In GitHub, what is a fork?',
      new Map<AnswerChoice, string>([
        [AnswerChoice.A, 'A way to permanently delete a repository'],
        [AnswerChoice.B, 'A copy of someone else’s repository that you can modify'],
        [AnswerChoice.C, 'A tool for merging branches'],
        [AnswerChoice.D, 'A feature that automatically runs tests'],
      ]),
      AnswerChoice.UNANSWERED,
    ); // Replace `UNANSWERED` with the correct answer.
  }

  private static makeQuestion1(): QuizQuestion {
    return new MultipleChoiceQuizQuestion(
      1,
      'After you fork a repository on GitHub, what is the typical next step?',
      new Map<AnswerChoice, string>([
        [AnswerChoice.A, 'Delete the forked repository'],
        [AnswerChoice.B, 'Clone the fork to your local machine'],
        [AnswerChoice.C, 'Merge the fork into the original repository'],
        [AnswerChoice.D, 'Change the repository visibility to private'],
      ]),
      AnswerChoice.UNANSWERED,
    ); // Replace `UNANSWERED` with the correct answer.
  }
    private static makeQuestion2(): QuizQuestion {
    return new MultipleChoiceQuizQuestion(
      2,
      'What is the correct way to delete a forked repository?',
      new Map<AnswerChoice, string>([
        [AnswerChoice.A, 'Click "unfork" in the repository settings'],
        [AnswerChoice.B, 'Go to Settings of the forked repository and click "Delete this repository"'],
        [AnswerChoice.C, 'In GitHub Desktop, select the fork and press "Delete"'],
        [AnswerChoice.D, 'Rename the repository to "delete" and Github will remove it automatically'],
      ]),
      AnswerChoice.UNANSWERED,
    ); // Replace `UNANSWERED` with the correct answer.
  }

}
