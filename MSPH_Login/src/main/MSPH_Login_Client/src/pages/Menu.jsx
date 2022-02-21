import React, { Component } from 'react';
import Cookies from 'universal-cookie';
const cookies = new Cookies();
export default class Menu extends Component {
    constructor(props){
        super(props);
        this.handleLogout = this.handleLogout.bind(this);
    }
    async handleLogout(e){
        (()=> {
            cookies.remove("username", {path: "/auth"});
            cookies.remove("full_name", {path: "/auth"});
            cookies.remove("roles", {path: "/auth"});
            cookies.remove("token", {path: "/auth"});
        }).then(this.props.history.push('/login'));
    }
    componentDidMount() {
        /*if (!(cookies.get('username',{path: "/auth"}) 
        && cookies.get('roles',{path: "/auth"}) 
        && cookies.get('token',{path: "/auth"})))
          this.props.history.push('/login');*/
        
    }
    render() {
        return (
            <div>
                <h1>Menu Principal</h1>
                <button onClick = {this.handleLogout}>Salir :v</button>
            </div>
        );
    }
}
