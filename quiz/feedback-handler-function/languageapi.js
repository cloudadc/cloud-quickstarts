const Language = require('@google-cloud/language');
const language = new Language.LanguageServiceClient();
function analyze(text) {
    const document = {
        content: text,
        type: 'PLAIN_TEXT'
    };
    return language.analyzeSentiment({ document }).then(results => {
        const sentiment = results[0];
        return sentiment.documentSentiment.score;
    });
}
module.exports = {
    analyze
};