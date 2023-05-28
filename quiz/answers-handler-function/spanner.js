const {Spanner} = require('@google-cloud/spanner');

const spanner = new Spanner();

const instance = spanner.instance('quiz-instance');
const database = instance.database('quiz-database');
const answerTable = database.table('answers');

async function saveAnswer(
    {id, email, quiz, timestamp, correct, answer}) {
    const record = {
          answerId:  `${quiz}_${email}_${id}_${timestamp}`,
          id,
          email,
          quiz,
          timestamp,
          correct,
          answer   
    };
    try {
        console.log('Saving answer');
        await answerTable.insert(record);
    
    } catch (err) {
        if (err.code === 6 ) {
            console.log("Duplicate message - answer already saved");
        } else {
            console.error('ERROR processing answer:', err);
        }
    }

}

module.exports = {
    saveAnswer
};
