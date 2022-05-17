import React from 'react';
import axios from 'axios';
import { Button, Row, Table, Container, Col } from 'react-bootstrap';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AddUserModal from './Components/AddUserModal';
import './Usuarios.css'
import NavigationBar from '../../components/NavigationBar';
import Cookies from 'universal-cookie';
import Pages from '../../components/Pages';
const cookies = new Cookies();

export default class Usuarios extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            usuarios: [],
            userView: [],
            AddUser: false,
            pageItemAmount: 10,
            currentPage: 1
        };
        this.refreshPage = this.refreshPage.bind(this);
        this.closeModalAdd = this.closeModalAdd.bind(this);
        this.openModalAdd = this.openModalAdd.bind(this);
        this.checkPermissions = this.checkPermissions.bind(this);
        this.checkOwner = this.checkOwner.bind(this);
        this.retrieveDepartments = this.retrieveDepartments.bind(this);
        this.updatePage = this.updatePage.bind(this);
        this.updatePageItems = this.updatePageItems.bind(this);
        this.handleUserRender = this.handleUserRender.bind(this);
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
        let options = {
            url: process.env.REACT_APP_AUTH_API_PATH + '/Users',
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
        axios(options)
            .then(response => {
                this.setState({
                    usuarios: response.data
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
                    this.handleUserRender();
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

    openModalAdd() {
        this.setState({ AddUser: true });
    };

    closeModalAdd() {
        this.setState({ AddUser: false });
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

    /* Pagination */
    updatePage(pageNumber) {
        this.setState({
            currentPage: pageNumber
        }, () => {
            this.handleUserRender();
        });
    }

    updatePageItems(amount) {
        this.setState({
            pageItemAmount: amount,
            currentPage: 1
        }, () => {
            this.handleUserRender();
        })
    }

    handleUserRender() {
        let items = [];
        let itemAmount = this.state.pageItemAmount;
        let pos = (this.state.currentPage - 1) * itemAmount;
        for (let i = 0; i < itemAmount; i++) {
            let item = this.state.usuarios.at(pos);
            if (typeof item !== 'undefined' && item !== null) {
                items.push(item);
            }
            pos++;
        }
        this.setState({ userView: items });
    }

    render() {
        return (
            <div className="Usuario-Container">
                <NavigationBar />
                {/* Mobile */}
                <div className='d-lg-none container-fluid'>
                    <Row className="mt-4">
                        {
                            (this.state.usuarios === null || typeof this.state.usuarios === 'undefined') ?
                                <div><h1>Cargando Datos</h1></div> :
                                <Container fluid>
                                    <Row>
                                        <Col>
                                            <h1>Lista de usuarios</h1>
                                            <div className="col-md-12 text-center">
                                                    <Button onClick={() => this.openModalAdd(this.state.user)}
                                                        disabled={(this.checkPermissions("USER") && !this.checkOwner()) ? true : false} id='btnEdit' >Agregar nuevo Usuario</Button>
                                                </div>
                                            <Table>
                                                <Table border="1" hover responsive="md">
                                                    <tbody>
                                                        {
                                                            (this.state.userView === null || typeof this.state.userView === 'undefined') ?
                                                                <option value={null} key="disabledUsuarios" disabled>Error al cargar los usuarios</option> :
                                                                this.state.userView.map((usu) => {
                                                                    return <tr>
                                                                    <td>
                                                                        <b>Usuario:</b>
                                                                    </td>
                                                                    <td>
                                                                        {usu.official.name + " " + usu.official.surname}
                                                                    </td>
                                                                    <td>
                                                                        <Button variant="link" href={"#/profile?id=" + usu.idUser}>+ Perfil del Usuario</Button>
                                                                    </td>
                                                                </tr>
                                                                })
                                                        }
                                                    </tbody>
                                                </Table>
                                            </Table>

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
                                    (this.state.usuarios === null || typeof this.state.usuarios === 'undefined') ?
                                        <h1>Cargando Datos</h1> :
                                        <div>

                                            <Table>
                                                <br />
                                                <h2 id='titulo'>Lista de Usuarios</h2>
                                                <div className="col-md-12 text-center">
                                                    <Button onClick={() => this.openModalAdd(this.state.user)}
                                                        disabled={(this.checkPermissions("USER") && !this.checkOwner()) ? true : false} id='btnEdit' >Agregar nuevo Usuario</Button>
                                                </div>
                                                <Table hover responsive="md">
                                                    <tbody>
                                                        {
                                                            (this.state.usuarios === null || typeof this.state.usuarios === 'undefined') ?
                                                                <option value={null} key="disabledUsuarios" disabled>Error al cargar los usuarios</option> :
                                                                this.state.usuarios.map((usu) => {
                                                                    return <tr>
                                                                        <td>
                                                                            <b>Usuario:</b>
                                                                        </td>
                                                                        <td>
                                                                            {usu.official.name + " " + usu.official.surname}
                                                                        </td>
                                                                        <td>
                                                                            <Button variant="link" href={"#/profile?id=" + usu.idUser}>+ Perfil del Usuario</Button>
                                                                        </td>
                                                                    </tr>
                                                                })
                                                        }
                                                    </tbody>
                                                </Table>
                                            </Table>
                                            <Pages
                                                listLength={this.state.usuarios.length}
                                                itemAmount={this.state.pageItemAmount}
                                                updatePage={this.updatePage}
                                                currentPage={this.state.currentPage}
                                                updatePageItems={this.updatePageItems} />

                                        </div>
                                }

                            </Col>
                        </Row>
                    </div>
                </div>
                <AddUserModal
                    show={this.state.AddUser}
                    departmentMap={this.state.departmentMap}
                    closeModal={this.closeModalAdd}
                    refreshPage={this.refreshPage}
                />
                <ToastContainer />
            </div >

        );
    }
}