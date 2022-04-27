import React, { Component } from 'react';
import axios from 'axios';
import { Row, Card, Table, Container, Col } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import GenericModal from '../../SharedComponents/GenericModal/GenericModal';
import Cookies from 'universal-cookie';
import EditPerfilModal from './Components/EditPerfilModal';
const cookies = new Cookies();

export default class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            showEdit: false
        };
        //this.refreshPage = this.refreshPage.bind(this);
    }

    componentDidMount() {
        //Account check
        if (typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('token', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('full_name', { path: process.env.REACT_APP_AUTH }) === 'undefined') {
            document.location = process.env.REACT_APP_LOGOUT;
        }
        this.closeModalEdit = this.closeModalEdit.bind(this);
        this.openModalEdit = this.openModalEdit.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
    }

    refreshPage() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/Users/get",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'user': query.get('username')
            }
        }
        axios(options)
            .then(response => {
                this.setState({
                    user: response.data
                }, () => {
                    if (this.state.user === null || typeof this.state.user === 'undefined') {
                        this.props.history.push('/perfil');
                    } else {
                        this.retrieveTypes();
                    }
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
        return (
            <div className="Plan-Container">
                user = {this.state.user}
                {/* Mobile */}
                <Row className="mt-4">
                    {
                        (this.state.user === null || typeof this.state.user === 'undefined') ?
                            <h1>Cargando Datos</h1> :
                            //Hay que hacer la validacion bien, este solo fue un ejemplo
                            (this.state.user.username === this.props.user.username) ?
                                <div>
                                    <h1>Nombre: {this.props.user.full_name}</h1>
                                    <h1>Usuario: {this.props.user.username}</h1>
                                    <h1>Departamento: {this.props.user.department}</h1>
                                    <h1>Rol: {this.props.user.roles}</h1>
                                    

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
                                    <h1>Nombre: {this.props.user.full_name}</h1>
                                    <h1>Usuario: {this.props.user.username}</h1>
                                    <h1>Departamento: {this.props.user.department}</h1>
                                    <h1>Rol: {this.props.user.roles}</h1>
                                    <Button onClick={() => this.openModalEdit(usuario)}>Editar Perfil</Button>


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