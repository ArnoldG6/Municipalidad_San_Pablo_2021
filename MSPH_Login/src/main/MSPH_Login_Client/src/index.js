/*
index.js (main module) for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React from 'react';
import ReactDOM from 'react-dom';
import Routes from './routes/Routes';
ReactDOM.render(
    /*
    ReactDOM.render(...) renders different SFR website's modules using "Routes" component.
    */
    <Routes/>, document.getElementById('root')
);

