import React from 'react';
import axios from 'axios';
import Cookies from 'universal-cookie';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Button, Row, Table, Card, Container, Col } from 'react-bootstrap';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import EditPerfilModal from './Components/EditPerfilModal';
import './Perfil.css'
import NavigationBar from '../../components/NavigationBar';
const cookies = new Cookies();

export default class Plan extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            showEdit: false,

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
        this.retrieveDepartments();
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
                }
                );
            })
            .catch(error => {
                this.props.history.push('/menu');
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
                /*let map = new Map();
                for (const [key, value] of Object.entries(response.data)) {
                    map.set(key, value);
                }
                console.log(map)*/
                this.setState({
                    departmentMap: response.data
                    //departmentMap: map
                }, () => {
                });
            }).catch(error => {
                toast.error("Error recuperando los departamentos", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });
    }

    render() {
        return (
            <div className="Usuario-Container">
                <NavigationBar />
                <div className='d-lg-none container-fluid'>
                    {/* Mobile */}
                    <Row className="mt-4">
                        {
                            (this.state.user === null || typeof this.state.user === 'undefined') ?
                                <div><h1>Cargando Datos</h1>
                                </div> :
                                <Container fluid>
                                    <Row>
                                        <Col>
                                            <h1>Perfil</h1>
                                            <Table>
                                                <Table border="1" hover responsive="md">
                                                    <tbody>
                                                        <tr><td><b>Usuario:</b></td><td>{this.state.user.username}</td></tr>
                                                        <tr><td><b>Nombre: </b></td><td>{this.state.user.full_name}</td></tr>
                                                        <tr><td><b>Email: </b></td><td>{this.state.user.email}</td></tr>
                                                        <tr><td><b>Departamento: </b></td><td>{this.state.user.department}</td></tr>
                                                        <tr><td><b>Rol: </b></td><td>{this.state.user.roles}</td></tr>

                                                    </tbody>
                                                </Table>
                                            </Table>
                                            <Button onClick={() => this.openModalEdit(this.state.user)} >Editar Perfil</Button>
                                        </Col>
                                    </Row>
                                </Container>
                        }
                    </Row>
                </div>
                {/* PC */}
                <div className="d-none d-lg-block">
                    <div className='container-fluid Data-container'>
                        {
                            (this.state.user === null || typeof this.state.user === 'undefined') ?
                                <div><h1>Cargando Datos</h1>
                                </div> :
                                <div>
                                    <Container>
                                        <Row>
                                            <Col>
                                                <Card>
                                                    <Card.Body>
                                                        <Card.Title>
                                                            <h2 id='titulo'>Perfil</h2>
                                                        </Card.Title>
                                                        <Table>
                                                            <Table border="1" hover responsive="md">
                                                                <tbody>
                                                                    <tr><td><b>Usuario:</b></td><td>{this.state.user.username}</td></tr>
                                                                    <tr><td><b>Nombre: </b></td><td>{this.state.user.full_name}</td></tr>
                                                                    <tr><td><b>Email: </b></td><td>{this.state.user.email}</td></tr>
                                                                    <tr><td><b>Departamento: </b></td><td>{this.state.user.department}</td></tr>
                                                                    <tr><td><b>Rol: </b></td><td>{this.state.user.roles}</td></tr>
                                                                </tbody>
                                                            </Table>
                                                        </Table>
                                                        <div class="col-md-12 text-center">
                                                            <Button onClick={() => this.openModalEdit(this.state.user)}
                                                                disabled={(this.checkPermissions("USER") && !this.checkOwner()) ? true : false} id='btnEdit' >Editar Perfil</Button>
                                                        </div>
                                                    </Card.Body>
                                                </Card>
                                            </Col>
                                        </Row>
                                    </Container>
                                </div>
                        }
                    </div>
                </div>
                <EditPerfilModal
                    user={this.state.user}
                    show={this.state.showEdit}
                    departmentMap={this.state.departmentMap}
                    closeModal={this.closeModalEdit}
                    refreshPage={this.refreshPage}

                />
            </div >

        );
    }
}