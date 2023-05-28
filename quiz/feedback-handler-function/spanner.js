const {Spanner} = require('@google-cloud/spanner');

const spanner = new Spanner();

const instance = spanner.instance('quiz-instance');
const database = instance.database('quiz-database');
const feedbackTable = database.table('feedback');


async function saveFeedback(
    { email, quiz, timestamp, rating, feedback, score }) {
    const rev_email = email
        .replace(/[@\.]/g, '_')
        .split('_')
        .reverse()
        .join('_');
    const record = {
        feedbackId: `${rev_email}_${quiz}_${timestamp}`,
        email,
        quiz,
        timestamp,
        rating,
        score: Spanner.float(score),
        feedback,
    };
     try {
        console.log('Saving feedback');
        await feedbackTable.insert(record);
    } catch (err) {
        if (err.code === 6 ) {
            console.log("Duplicate message - feedback already saved");
        } else {
            console.error('ERROR processing feedback:', err);
        }
    }
}

module.exports = {
    saveFeedback
};