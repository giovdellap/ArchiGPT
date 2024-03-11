const path = require('path')
const defaultProfile = require(path.join(__dirname.split('src')[0], 'src' , 'Models', 'product_profile'))

class Parser{
    
    constructor(){
    }

    marshalRequestToProfile(requestBody){
        let productProfile = JSON.parse(JSON.stringify(defaultProfile))
        //Update product Name
        productProfile['name'] = requestBody['product']

        //Update number of samples
        productProfile['no_of_samples'] = parseInt(requestBody['num_prod'])
        
        //Update Education
        if(requestBody['Education'] != null){
            productProfile['Education'][requestBody['Education']]++
        }
        else{
            for (let subkey in productProfile['Education']){
                productProfile['Education'][subkey] += productProfile['no_of_samples']/4
            }
        }
        
        //Update Purchase Methods
        if(requestBody['purchase_method'] != null){
            productProfile['purchase_method'][requestBody['purchase_method']]++
        }
        else{
            for (let subkey in productProfile['purchase_method']){
                productProfile['purchase_method'][subkey] += productProfile['no_of_samples']/2
            }
        }
    
        //Update Marital Status
        if(requestBody['Marital_Status'] != null){
            productProfile['Marital_Status'][requestBody['Marital_Status']]++
        }
        else{
            for (let subkey in productProfile['Marital_Status']){
                productProfile['Marital_Status'][subkey] += productProfile['no_of_samples']/4
            }
        }
        
        //Update Year of Birth
        if(requestBody['Year_Birth'] != null){
            requestBody['Year_Birth'] = parseInt(requestBody['Year_Birth'])
            if(requestBody['Year_Birth'] < 1960){
                productProfile['Year_Birth']['< 1960']++
            }
            else if(requestBody['Year_Birth'] >= 1960 && requestBody['Year_Birth'] < 1975){
                productProfile['Year_Birth']['1960_1975']++
            }
            else{
                productProfile['Year_Birth']['> 1975']++
            }
        }
        else{
            for (let subkey in productProfile['Year_Birth']){
                productProfile['Year_Birth'][subkey] += productProfile['no_of_samples']/4
            }
        }
    
        //Update Income
        if(requestBody['Income'] != null){
            requestBody['Income'] = parseInt(requestBody['Income'])
            if(requestBody['Income'] < 30000){
                productProfile['Income']['< 30k']++
            }
            else if(requestBody['Income'] >= 30000 && requestBody['Income'] < 50000){
                productProfile['Income']['30k_50k']++
            }
            else if(requestBody['Income'] >= 50000 && requestBody['Income'] < 75000){
                productProfile['Income']['50k_75k']++
            }
            else{
                productProfile['Income']['> 75k']++
            }
        }
        else{
            for (let subkey in productProfile['Income']){
                productProfile['Income'][subkey] += productProfile['no_of_samples']/4
            }
        }
    
        //Update Kids
        if(requestBody['Kids'] != null){
            if(requestBody['Kids'] > 0){
                productProfile['Kids']['kids']++
            }
            else{
                productProfile['Kids']['No kids']++
            }
        }
        else{
            for (let subkey in productProfile['Kids']){
                productProfile['Kids'][subkey] += productProfile['no_of_samples']/2
            }
        }
    
        return productProfile
    }

    updateExistingProfile(oldProfile,newProfile){
        for (var key in oldProfile){
            if (key == 'name'){
                oldProfile[key] = newProfile[key]
            }
            else if (key == 'infered_samples'){
                oldProfile[key] += newProfile[key]
            }
            else if(key == 'no_of_samples'){
                oldProfile[key] += newProfile[key]
            }
            else if(key == '_id'){
                //Do nothing
            }
            else{
                for(var subkey in oldProfile[key]){
                    oldProfile[key][subkey] += newProfile[key][subkey]
                }
            }
        }
    }

    modifyLogFieldsTypes(log){
        log['ID'] = parseInt(log['ID'])
        log['Year_Birth'] = parseInt(log['Year_Birth'])
        log['Income'] = parseInt(log['Income'])
        log['Kids'] = parseInt(log['Kids'])
        log['num_prod'] = parseInt(log['num_prod'])
    }
}

module.exports = Parser