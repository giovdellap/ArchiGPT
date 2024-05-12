const { Client } = require('@elastic/elasticsearch')
const client = new Client({ node: 'http://elasticsearch:9200' })

async function create(id, nick) {
    try {
      await client.create({
        index: 'game',
        id: id,
        body: {
          nickname: nick,
          score: 0
        }
      })
    }
    catch (error) {
      console.log("error")
    }
  
}

async function update(id, score){
    await client.update({
        index: 'game',
        id: id,
        body: {
          script: {
            lang: "painless",
            source: "ctx._source.score += params['newScore']",
            params: {
                newScore: score
            }
          }
        },
        retry_on_conflict: 1000
    })
}

async function update_questions(id, letter){
  await client.update({
      index: 'questions',
      id: id,
      body: {
        script: {
          lang: "painless",
          source: "ctx._source."+letter+" ++"
        }
      },
      retry_on_conflict: 1000
  })
}


async function get(id) {
  try{
    const { body } = await client.get({
      index: 'game',
      id: id
    })

    score = body._source.score
    
    return score;
  }
  catch {
    console.log("errore nella chiamata get al db: "+id);
  }
  
}



module.exports.create = create;
module.exports.update = update;
module.exports.update_questions = update_questions;
module.exports.get = get;

  