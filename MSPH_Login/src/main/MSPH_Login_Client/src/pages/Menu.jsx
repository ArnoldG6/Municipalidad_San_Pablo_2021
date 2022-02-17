import React, { Component } from 'react';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

export default class Menu extends Component {
    render() {
        return (
            <div>
                Menu Principal
            </div>
        );
    }
}
