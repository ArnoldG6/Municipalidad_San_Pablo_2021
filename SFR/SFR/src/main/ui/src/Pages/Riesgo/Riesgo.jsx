import React, { Component } from 'react';
import axios from 'axios';
import { Row, Table, Container, Col } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import TopButtons from './Components/TopButtons';
import EditRiskModal from './Components/EditRiskModal';
import GenericModal from '../../SharedComponents/GenericModal/GenericModal';
import Cookies from 'universal-cookie';
import './Riesgo.css'
const cookies = new Cookies();

export default class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            risk: null,
            typesMap: null,
            showDel: false,
            showEdit: false,
            planCount: 0
        };
        
        this.refreshPage = this.refreshPage.bind(this);
        this.openModalDelete = this.openModalDelete.bind(this);
        this.closeModalDelete = this.closeModalDelete.bind(this);
        this.openModalEdit = this.openModalEdit.bind(this);
        this.closeModalEdit = this.closeModalEdit.bind(this);
        this.deleteRisk = this.deleteRisk.bind(this);
        this.retrieveTypes = this.retrieveTypes.bind(this);
    }

    componentDidMount() {
        //Account check
        if (typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('full_name', { path: process.env.REACT_APP_AUTH }) === 'undefined') {
            document.location = process.env.REACT_APP_SIMSP_LOGOUT;
        }

        this.refreshPage();
    }

   

    refreshPage() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/RiskServlet/Retrieve/Riesgo",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'riskID': query.get('id')
            }
        }
        axios(options)
            .then(response => {
                this.setState({
                    risk: response.data
                }, () => {
                    if (this.state.risk === null || typeof this.state.risk === 'undefined') {
                        this.props.history.push('/riesgos');
                    } else {
                        this.retrieveTypes();
                    }
                });
            })
            .catch(error => {
                this.props.history.push('/riesgos');
            });
    }

    retrieveTypes() {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + `/RiskServlet/Retrieve/RiskType`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }
        axios(options)
            .then(response => {
                let map = new Map();
                for (const [key, value] of Object.entries(response.data)) {
                    map.set(key, value);
                }
                this.setState({
                    typesMap: map
                });
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema recuperando los Tipos de Riesgos.";
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
            })
    }

    openModalEdit = () => {
        this.setState({ showEdit: true });
    };

    closeModalEdit = () => {
        this.setState({ showEdit: false });
    };

    openModalDelete = () => {
        this.setState({ showDel: true });
    };

    closeModalDelete = () => {
        this.setState({ showDel: false });
    };

    deleteRisk() {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + `/RiskManager/Delete`,
            method: 'DELETE',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'pkID': this.state.risk.pkID,
                'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
            }
        }
        axios(options)
            .then(response => {
                this.props.history.push('/riesgos');
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema encontrando el Riesgo a eliminar.";
                            break;
                        case 401:
                            msj = "Este usuario no cuenta con permisos para eliminar Riesgos.";
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
            })
    }

    render() {
        return (
            <div className="Plan-Container">
                {/* Mobile */}
                <div className='d-lg-none container-fluid'>
                    <Row>
                        <TopButtons
                            openModalEdit={this.openModalEdit}
                            openModalDelete={this.openModalDelete}
                            risk={this.state.risk} />
                    </Row>
                    {/* Datos del Plan */}
                    <Row className="mt-4">
                        {
                            (this.state.risk === null || typeof this.state.risk === 'undefined') ?
                                <h1>Cargando Datos</h1> :
                                <Container fluid>
                                    <Row>
                                        <Col>
                                            <h1>{this.state.risk.name}</h1>
                                            <h2>ID: {this.state.risk.id}</h2>
                                            <h4>Autor: {this.state.risk.author.official.name} {this.state.risk.author.official.surname}</h4>
                                            <Table>
                                                <h2>Información General</h2>
                                                <Table border="1" hover responsive="md">
                                                    <tbody>
                                                        <tr><td><b>Tipo General</b></td><td>{this.state.risk.generalType}</td></tr>
                                                        <tr><td><b>Tipo Por Área</b></td><td>{this.state.risk.areaType}</td></tr>
                                                        <tr><td><b>Tipo Por Área Específico</b></td><td>{this.state.risk.areaSpecificType}</td></tr>
                                                        <tr><td><b>Probabilidad</b></td><td>{this.state.risk.probability}</td></tr>
                                                        <tr><td><b>Impacto</b></td><td>{this.state.risk.impact}</td></tr>
                                                        <tr><td><b>Magnitud</b></td><td>{this.state.risk.magnitude}</td></tr>
                                                        <tr><td><b>Factores</b></td><td>{this.state.risk.factors}</td></tr>
                                                        <tr><td><b>Description</b></td><td>{this.state.risk.description}</td></tr>
                                                    </tbody>
                                                </Table>
                                            </Table>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col>
                                            <Table>
                                                <h2>Información Estadística</h2>
                                                <Table border="1" hover responsive="md">
                                                    <tbody>
                                                        <tr><td>Cantidad de Planes en Donde Este Riesgo Está Presente</td><td>{this.state.risk.planCount}</td></tr>
                                                        <tr><td><b>Cantidad de Veces que se ha Presentado Este Riesgo</b></td><td>{this.state.risk.incidenceCount}</td></tr>
                                                        <tr><td><b>Factor de Ocurrencia</b></td><td>{this.state.risk.occurrenceFactor}</td></tr>
                                                    </tbody>
                                                </Table>
                                                <div>&nbsp;</div>
                                                <h2>Medidas de Mitigación</h2>
                                                <Table border="1" hover responsive="md">
                                                    <tbody>
                                                        <tr><td>{this.state.risk.mitigationMeasures}</td></tr>
                                                    </tbody>
                                                </Table>
                                                <div>&nbsp;</div>
                                                <h2>Consecuencias</h2>
                                                <Table border="1" hover responsive="md">
                                                    <tbody>
                                                        <tr><td>{this.state.risk.consequences}</td></tr>
                                                    </tbody>
                                                </Table>

                                            </Table>
                                        </Col>
                                    </Row>
                                </Container>
                        }
                    </Row>
                </div>
                {/* Vista Desktop */}
                <div className="d-none d-lg-block">
                    <div className="container-fluid Data-Container">
                        {/* Botones de uso en el Riesgo */}
                        <Row>
                            <TopButtons
                                openModalEdit={this.openModalEdit}
                                openModalDelete={this.openModalDelete}
                                risk={this.state.risk} />
                        </Row>
                        {/* Datos del Riesgo */}
                        <Row className="mt-4">
                            {
                                (this.state.risk === null || typeof this.state.risk === 'undefined') ?
                                    <h1>Cargando Datos</h1> :
                                    <div>
                                        

                                            <h1>{this.state.risk.name}</h1>
                                            <h2>ID: {this.state.risk.id}</h2>
                                            <h4>Autor: {this.state.risk.author.official.name} {this.state.risk.author.official.surname}</h4>

                                            <Row>
                                                <Col>


                                                    <Table borderless="true">
                                                        <h2>Información General</h2>
                                                        <Table   hover responsive="md" >
                                                            <tbody>
                                                                <tr><td className='tdRisk'><b>Tipo General</b></td><td>{this.state.risk.generalType}</td></tr>
                                                                <tr><td className='tdRisk'><b>Tipo Por Área</b></td><td>{this.state.risk.areaType}</td></tr>
                                                                <tr><td className='tdRisk'><b>Tipo Por Área Específico</b></td><td>{this.state.risk.areaSpecificType}</td></tr>
                                                                <tr><td className='tdRisk'><b>Probabilidad</b></td><td>{this.state.risk.probability}</td></tr>
                                                                <tr><td className='tdRisk'><b>Impacto</b></td><td>{this.state.risk.impact}</td></tr>
                                                                <tr><td className='tdRisk'><b>Magnitud</b></td><td>{this.state.risk.magnitude}</td></tr>
                                                                <tr><td className='tdRisk'><b>Factores</b></td><td>{this.state.risk.factors}</td></tr>
                                                                <tr><td className='tdRisk'><b>Descripción</b></td><td>{this.state.risk.description}</td></tr>
                                                            </tbody>
                                                        </Table>
                                                    </Table>


                                                </Col>
                                                <Col>

                                                    <Table borderless="true">
                                                        <h2>Información Estadística</h2>
                                                        <Table  hover responsive="md">
                                                            <tbody>
                                                                <tr><td className='tdRisk'><b>Cantidad de Planes en Donde Este Riesgo Está Presente</b></td><td>{this.state.risk.planCount}</td></tr>
                                                                <tr><td className='tdRisk'><b>Cantidad de Veces que se ha Presentado Este Riesgo</b></td><td>{this.state.risk.incidenceCount}</td></tr>
                                                                <tr><td className='tdRisk'><b>Factor de Ocurrencia</b></td><td>{this.state.risk.occurrenceFactor}</td></tr>
                                                            </tbody>
                                                        </Table>
                                                        <div>&nbsp;</div>
                                                        <h2>Medidas de Mitigación</h2>
                                                        <Table  hover responsive="md">
                                                            <tbody>
                                                                <tr><td className='tdRisk'>{this.state.risk.mitigationMeasures}</td></tr>
                                                            </tbody>
                                                        </Table>
                                                        <div>&nbsp;</div>
                                                        <h2>Consecuencias</h2>
                                                        <Table hover responsive="md">
                                                            <tbody>
                                                                <tr><td className='tdRisk'>{this.state.risk.consequences}</td></tr>
                                                            </tbody>
                                                        </Table>

                                                    </Table>

                                                </Col>
                                            </Row>
                                        
                                    </div>
                            }
                        </Row>
                    </div>
                </div>
                <EditRiskModal
                    refreshPage={this.refreshPage}
                    risk={this.state.risk}
                    show={this.state.showEdit}
                    closeModalEdit={this.closeModalEdit}
                    typesMap={this.state.typesMap}
                />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelete}
                    action={this.deleteRisk}
                    header={"Eliminar Riegos"}
                    body={"¿Desea eliminar este riesgo? Una vez eliminado no se podra recuperar el riesgo seleccionado"}
                />
                <ToastContainer />
            </div>
        );
    }
}
