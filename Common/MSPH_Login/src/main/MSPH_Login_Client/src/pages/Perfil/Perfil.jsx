import React from 'react';
import axios from 'axios';
import { Button, Row, Table, Container, Col } from 'react-bootstrap';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import EditPerfilModal from './Components/EditPerfilModal';
import './Perfil.css'
import NavigationBar from '../../components/NavigationBar';
import Cookies from 'universal-cookie';
const cookies = new Cookies();
//Se cambio Plan y Pefil
export default class Perfil extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            showEdit: false
        };
        this.refreshPage = this.refreshPage.bind(this);
        this.closeModalEdit = this.closeModalEdit.bind(this);
        this.openModalEdit = this.openModalEdit.bind(this);
        this.checkPermissions = this.checkPermissions.bind(this);
        this.checkOwner = this.checkOwner.bind(this);
        this.retrieveDepartments = this.retrieveDepartments.bind(this);
    }

    componentDidMount() {
        //Account check
        if (typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
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
                    this.retrieveDepartments();
                });
            })
            .catch(error => {
                this.props.history.push('/menu');
            });
    }

    retrieveDepartments() {
        let options = {
            url: process.env.REACT_APP_AUTH_API_PATH + `/Department`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
        axios(options)
            .then(response => {
                this.setState({
                    departmentMap: response.data
                }, () => {
                    //this.handleUserRender();
                });
            }).catch(error => {
                var msj = ""
                if (error.response) {
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema cargando los departamentos.";
                            break;
                        case 500:
                            msj = "El servidor ha encontrado un error desconocido.";
                            break;
                        default:
                            msj = "El servidor ha encontrado un error desconocido.";
                            break;
                    }
                } else if (error.request) {
                    //Server did not respond
                    msj = "Hubo un error con la conexión al servidor."
                } else {
                    //Something else went wrong
                    msj = "Error desconocido."
                }
                toast.error(msj, {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });
    }

    openModalEdit(usu) {
        this.setState({ showEdit: true });
    };

    closeModalEdit() {
        this.setState({ showEdit: false });
    };

    checkPermissions(toCheck) {
        let perm = false;
        if (typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) !== 'undefined') {
            cookies.get('roles', { path: process.env.REACT_APP_AUTH }).map((rol) => {
                if (rol.description === toCheck) {
                    perm = true;
                    return true;
                }
                return false;
            })
        }
        return perm;
    }

    checkOwner() {
        let perm = false;
        if ((typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) !== 'undefined') && (this.state.user.username !== null) && (typeof this.state.user.username !== 'undefined')) {
            if (cookies.get('username', { path: process.env.REACT_APP_AUTH }) === this.state.user.username.toString()) {
                perm = true;
            }
        }
        return perm;
    }

    render() {
        return (
            <div className="Usuario-Container">
                <NavigationBar />
                {/* Mobile */}
                <div className='d-lg-none container-fluid'>
                    <Row className="mt-4">
                        {
                            (this.state.user === null || typeof this.state.user === 'undefined') ?
                                <div><h1>Cargando Datos</h1></div> :
                                <Container fluid>
                                    <Row>
                                        <Col>
                                            <h1>Perfil</h1>
                                            <Table>
                                                <Table border="1" hover responsive="md">
                                                    <tbody>
                                                        <tr><td><b>Usuario:</b></td><td>{this.state.user.username}</td></tr>
                                                        <tr><td><b>Email: </b></td><td>{this.state.user.email}</td></tr>
                                                        <tr><td><b>Nombre: </b></td><td>{this.state.user.name}</td></tr>
                                                        <tr><td><b>Apellido: </b></td><td>{this.state.user.surname}</td></tr>
                                                        <tr><td><b>Departamento: </b></td><td>{this.state.user.department}</td></tr>
                                                        <tr><td><b>Rol: </b></td><td>{this.state.user.roles}</td></tr>

                                                    </tbody>
                                                </Table>
                                            </Table>

                                            {(this.checkOwner() || this.checkPermissions("SUPER_ADMIN")) ?
                                                <div className="col-md-12 text-center">
                                                    <Button onClick={() => this.openModalEdit(this.state.user)} id='btnEdit' >Editar Perfil</Button>
                                                </div> :
                                                <div></div>
                                            }
                                        </Col>
                                    </Row>
                                </Container>
                        }
                    </Row>
                </div>
                {/* PC */}
                <div className="d-none d-lg-block">
                    <div className='container-fluid Data-container'>
                        <Row>
                            <Col md={{ span: 6, offset: 3 }}>
                                {
                                    (this.state.user === null || typeof this.state.user === 'undefined') ?
                                        <h1>Cargando Datos</h1> :
                                        <div>
                                            <Table>
                                                <br />
                                                <h2 id='titulo'>Perfil</h2>
                                                <Table hover responsive="md">
                                                    <tbody>
                                                        <tr><td><b>Usuario:</b></td><td>{this.state.user.username}</td></tr>
                                                        <tr><td><b>Email: </b></td><td>{this.state.user.email}</td></tr>
                                                        <tr><td><b>Nombre: </b></td><td>{this.state.user.name}</td></tr>
                                                        <tr><td><b>Apellido: </b></td><td>{this.state.user.surname}</td></tr>
                                                        <tr><td><b>Departamento: </b></td><td>{this.state.user.department}</td></tr>
                                                        <tr><td><b>Rol: </b></td><td>{this.state.user.roles}</td></tr>
                                                    </tbody>
                                                </Table>
                                            </Table>
                                            {(this.checkOwner() || this.checkPermissions("SUPER_ADMIN")) ?
                                                <div className="col-md-12 text-center">
                                                    <Button onClick={() => this.openModalEdit(this.state.user)} id='btnEdit' >Editar Perfil</Button>
                                                </div> :
                                                <div></div>
                                            }
                                        </div>
                                }

                            </Col>
                        </Row>
                    </div>
                </div>
                <EditPerfilModal
                    user={this.state.user}
                    show={this.state.showEdit}
                    departmentMap={this.state.departmentMap}
                    closeModal={this.closeModalEdit}
                    refreshPage={this.refreshPage}

                />
                <ToastContainer />
            </div >

        );
    }
}