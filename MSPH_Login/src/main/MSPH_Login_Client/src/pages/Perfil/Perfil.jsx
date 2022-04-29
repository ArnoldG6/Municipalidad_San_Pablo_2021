import React from 'react';
import axios from 'axios';
import Cookies from 'universal-cookie';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Button, Row, Table } from 'react-bootstrap';
import EditPerfilModal from './Components/EditPerfilModal';
import './Perfil.css'
const cookies = new Cookies();

export default class Plan extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
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
            url: process.env.REACT_APP_AUTH_API_PATH + '/User',
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'username': query.get('id')
            }
        }
        axios(options)
            .then(response => {
                this.setState({
                    user: response.data
                }, () => {
                    console.log(this.state.user)
                });
            })
            .catch(error => {
                this.props.history.push('/menu');
            });
    }

    openModalEdit(usu) {
        this.setState({ showEdit: true, user: this.state.user });
    };

    closeModalEdit() {
        this.setState({ showEdit: false });
    };

    render() {
        let logeado = cookies.get('username', { path: process.env.REACT_APP_AUTH });
        let roles = cookies.get('roles', { path: process.env.REACT_APP_AUTH });
        return (
            <div className="Usuario-Container">
                <div className='d-lg-none container-fluid'>
                    {/* Mobile */}
                    <Row className="mt-4">
                        {
                            (this.state.user === null || typeof this.state.user === 'undefined') ?
                                <div><h1>Cargando Datos</h1>
                                </div> :
                                <div>
                                    <Table>
                                        <h2>Perfil</h2>
                                        <Table border="1" hover responsive="md">
                                            <tbody>
                                                <tr><td><b>Usuario:</b></td><td>{this.state.user.username}</td></tr>
                                                <tr><td><b>Nombre: </b></td><td>{this.state.user.full_name}</td></tr>
                                                <tr><td><b>Email: </b></td><td>{this.state.user.email}</td></tr>
                                                <tr><td><b>Departamento: </b></td><td>{this.state.user.department}</td></tr>
                                                {//<tr><td><b>Rol: </b></td><td>{this.state.user.roles}</td></tr>
                                                }
                                            </tbody>
                                        </Table>
                                    </Table>
                                    <Button onClick={() => this.openModalEdit(this.state.user)}>Editar Perfil</Button>
                                </div>
                        }
                    </Row>
                    {/* PC */}
                    <div className="d-none d-lg-block">
                        <div className='container-fluid Data-container'>
                            {
                                (this.state.user === null || typeof this.state.user === 'undefined') ?
                                    <div><h1>Cargando Datos</h1>
                                    </div> :
                                    <div>
                                        <Table>
                                            <h2>Perfil</h2>
                                            <Table border="1" hover responsive="md">
                                                <tbody>
                                                    <tr><td><b>Usuario:</b></td><td>{this.state.user.username}</td></tr>
                                                    <tr><td><b>Nombre: </b></td><td>{this.state.user.full_name}</td></tr>
                                                    <tr><td><b>Email: </b></td><td>{this.state.user.email}</td></tr>
                                                    <tr><td><b>Departamento: </b></td><td>{this.state.user.department}</td></tr>
                                                    {//<tr><td><b>Rol: </b></td><td>{this.state.user.roles}</td></tr>
                                                    }
                                                </tbody>
                                            </Table>
                                        </Table>
                                        <Button onClick={() => this.openModalEdit(this.state.user)}>Editar Perfil</Button>
                                    </div>
                            }
                        </div>
                    </div>
                    <EditPerfilModal
                        user={this.state.user}
                        show={this.state.showEdit}
                        closeModal={this.closeModalEdit}
                        refreshPage={this.refreshPage}

                    />
                </div>
            </div>
        );
    }
}