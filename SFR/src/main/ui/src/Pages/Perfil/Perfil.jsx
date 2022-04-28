import React, { Component } from 'react';
import axios from 'axios';
import { Row, Button } from "react-bootstrap";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Cookies from 'universal-cookie';
import EditPerfilModal from './Components/EditPerfilModal';
const cookies = new Cookies();

export default class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            official: null,
            showEdit: false
        };
        this.refreshPage = this.refreshPage.bind(this);
        this.closeModalEdit = this.closeModalEdit.bind(this);
        this.openModalEdit = this.openModalEdit.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
    }

    componentDidMount() {
        //Account check
        if (typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('token', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('full_name', { path: process.env.REACT_APP_AUTH }) === 'undefined') {
            document.location = process.env.REACT_APP_LOGOUT;
        }
        this.refreshPage();
    }

    refreshPage() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/User",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'username': query.get('username')
            }
        }
        axios(options)
            .then(response => {
                this.setState({
                    user: response.data
                }, () => {
                });
            })
            .catch(error => {
                this.props.history.push('/perfil');
            });
    }

    openModalEdit(usu) {
        this.setState({ showEdit: true, usuario: usu });
    };

    closeModalEdit() {
        this.setState({ showEdit: false });
    };

    render() {
        let logeado = cookies.get('username', { path: process.env.REACT_APP_AUTH });
        let roles = cookies.get('roles', { path: process.env.REACT_APP_AUTH });
        return (
            <div className="Plan-Container">

                {/* Mobile */}
                <Row className="mt-4">
                    {
                        (this.state.user === null || typeof this.state.user === 'undefined') ?
                            <div><h1>Cargando Datos</h1>
                            </div> :
                            (this.state.user.username === logeado || roles === "SUPER_ADMIN") ?
                                <div>
                                    <h1>Usuario: {this.state.user.username}</h1>
                                    <h1>Nombre: {this.state.user.full_name}</h1>
                                    <h1>Gmail: {this.state.user.email}</h1>
                                    <h1>Departamento: {this.state.user.department}</h1>
                                    <h1>Rol: {this.state.user.roles}</h1>


                                </div> :
                                <div>
                                    <h1>Nombre: {this.state.user.full_name}</h1>
                                    <h1>Usuario: {this.state.user.username}</h1>
                                    <h1>Departamento: {this.state.user.department}</h1>
                                    <h1>Rol: {this.state.user.roles}</h1>
                                </div>
                    }
                </Row>
                {/* Vista Desktop */}
                <div className="d-none d-lg-block">
                    {
                        (this.state.user === null || typeof this.state.user === 'undefined') ?
                            <h1>Cargando Datos</h1> :
                            (this.state.user.username === this.props.username || this.state.user.roles === "SUPER_ADMIN") ?
                                <div>
                                    <h1>Usuario: {this.state.user.username}</h1>
                                    <h1>Nombre: {this.state.user.full_name}</h1>
                                    <h1>Gmail: {this.state.user.email}</h1>
                                    <h1>Departamento: {this.state.user.department}</h1>
                                    <h1>Rol: {this.state.user.roles}</h1>
                                    <Button onClick={() => this.openModalEdit(this.state.user)}>Editar Perfil</Button>
                                </div> :
                                <div>
                                    <h1>Nombre: {this.state.user.full_name}</h1>
                                    <h1>Usuario: {this.state.user.username}</h1>
                                    <h1>Departamento: {this.state.user.department}</h1>
                                    <h1>Rol: {this.state.user.roles}</h1>
                                </div>
                    }
                </div>
                <EditPerfilModal
                    user={this.state.user}
                    show={this.state.showEdit}
                    closeModal={this.closeModalEdit}
                    refreshPage={this.refreshPage}
                />
                <ToastContainer />
            </div>
        );
    }
}