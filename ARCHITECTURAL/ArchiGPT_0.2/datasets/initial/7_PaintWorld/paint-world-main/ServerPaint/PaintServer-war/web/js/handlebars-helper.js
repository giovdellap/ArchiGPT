function logicalCondition(v1, operator, v2, options) {
    switch (operator) {
        case '==':
            return (v1 == v2) ? options.fn(this) : options.inverse(this);
        case '===':
            return (v1 === v2) ? options.fn(this) : options.inverse(this);
        case '!=':
            return (v1 != v2) ? options.fn(this) : options.inverse(this);
        case '!==':
            return (v1 !== v2) ? options.fn(this) : options.inverse(this);
        case '<':
            return (v1 < v2) ? options.fn(this) : options.inverse(this);
        case '<=':
            return (v1 <= v2) ? options.fn(this) : options.inverse(this);
        case '>':
            return (v1 > v2) ? options.fn(this) : options.inverse(this);
        case '>=':
            return (v1 >= v2) ? options.fn(this) : options.inverse(this);
        case '&&':
            return (v1 && v2) ? options.fn(this) : options.inverse(this);
        case '||':
            return (v1 || v2) ? options.fn(this) : options.inverse(this);
        default:
            return options.inverse(this);
    }
}
function computation(v1, operator, v2) {
    switch (operator) {
        case '%':
            return v1 % v2;
        case '/':
            return v1 / v2;
        case '+':
            return v1 + v2;
        case '-':
            return v1 - v2;
    }
}
var aporaCondition = function (v1, operator, v2, options) {
    return logicalCondition(v1, operator, v2, options);
};
var aporaConditionAndMath = function (v1, operator1, v2, operator2, v3, options) {
    var v = computation(v1, operator1, v2);
    return logicalCondition(v, operator2, v3, options);
};
Handlebars.registerHelper('ifCondL', aporaCondition);
Handlebars.registerHelper('ifCondML', aporaConditionAndMath);