import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import { Container, Button } from 'react-bootstrap';
const cookies = new Cookies();
export default class Menu extends Component {
    constructor(props) {
        super(props);
        this.handleLogout = this.handleLogout.bind(this);
    }
    async handleLogout(e) {
        cookies.remove("username", { path: "/auth" });
        cookies.remove("full_name", { path: "/auth" });
        cookies.remove("roles", { path: "/auth" });
        cookies.remove("token", { path: "/auth" });
        this.props.history.push('/login');
    }
    componentDidMount() {

        if (!(cookies.get('username', { path: "/auth" })
            && cookies.get('roles', { path: "/auth" })
            && cookies.get('token', { path: "/auth" })
            && cookies.get('full_name', { path: "/auth" })))
            this.props.history.push('/login');

    }
    render() {
        return (
            <div>
                <h1 className="text-center">Menu Principal</h1>
                <Container className="w-auto text-center mx-auto p-3 mt-2 container">
                    <div className="text-center">
                        <Button onClick={() => {document.location = "http://localhost:8080/SFR/#/planes";}}>
                            SFR
                        </Button>
                    </div>
                    <div className="text-center">
                        <Button onClick={this.handleLogout}>
                            Salir :v
                        </Button>
                    </div>
                </Container>
            </div>
        );
    }
}
