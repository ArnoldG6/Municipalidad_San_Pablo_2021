import React, { Component } from 'react';
import axios from 'axios';
import { Row, Card, Nav, Table, Container, Col } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import TopButtons from './Components/TopButtons';
import EditRiskModal from './Components/EditRiskModal';
import GenericModal from '../../SharedComponents/GenericModal/GenericModal';
//import Cookies from 'universal-cookie';
//const cookies = new Cookies();

export default class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            risk: null,
            typesMap: null,
            showDel: false,
            showEdit: false,
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
        this.refreshPage();
    }

    refreshPage() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_API_URL + "/RiskServlet/Retrieve/Riesgo",
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
            }).catch(error => {
                this.props.history.push('/riesgos');
            });
    }

    retrieveTypes() {
        let options = {
            url: process.env.REACT_APP_API_URL + `/RiskServlet/Retrieve/RiskType`,
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
            }).catch(error => {
                toast.error("Error recuperando los tipos/subtipos de Riesgos", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });
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
            url: process.env.REACT_APP_API_URL + `/RiskManager/Delete`,
            method: 'DELETE',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'pkID': this.state.delId
            }
        }
        axios(options)
            .then(response => {
                this.props.history.push('/riesgos');
            })
    }

    render() {
        //let tableData = this.tableAssign();
        let tableData = "some wea"
        //let a = this
        return (
            <div className="Plan-Container">
                {/* Mobile */}
                <div className='d-lg-none container-fluid'>
                    <Row>
                        <TopButtons
                            openModalEdit={this.openModalEdit}
                            openModalDelete={this.openModalDelete}
                            status={"Some wea"} />
                    </Row>
                    {/* Datos del Plan */}
                    <Row className="mt-4">
                        {
                            (this.state.risk === null || typeof this.state.risk === 'undefined') ?
                                <h1>Cargando Datos</h1> :
                                <div>
                                    <h1>{this.state.risk.name}</h1>
                                    <h2>{this.state.risk.id}</h2>
                                    <h2>{this.state.risk.generalType}</h2>
                                    <h4>{this.state.risk.areaType}</h4>
                                    <h4>{this.state.risk.specType}</h4>
                                    <p>{this.state.risk.factors}</p>
                                </div>
                        }
                    </Row>
                    <Card id="card">
                        <Card.Header>
                            <Nav fill variant="tabs" defaultActiveKey="riesgosTab">
                                <Nav.Item>
                                    <Nav.Link eventKey="riesgosTab">Riesgos</Nav.Link>
                                </Nav.Item>
                            </Nav>
                        </Card.Header>
                        <Card.Body>
                            {tableData}
                        </Card.Body>
                    </Card>
                </div>
                {/* Vista Desktop */}
                <div className="d-none d-lg-block">
                    <div className="container-fluid Data-Container">
                        {/* Botones de uso en el Plan */}
                        <Row>
                            <TopButtons
                                openModalEdit={this.openModalEdit}
                                openModalDelete={this.openModalDelete}
                                status={"status"} />
                        </Row>
                        {/* Datos del Plan */}
                        <Row className="mt-4">
                            {
                                (this.state.risk === null || typeof this.state.risk === 'undefined') ?
                                    <h1>Cargando Datos</h1> :
                                    <div>
                                        <h1>{this.state.risk.id}</h1>
                                        <h1>{this.state.risk.name}</h1>
                                        <Container>
                                            <Row>
                                                <Col>
                                                    <Card>
                                                        <Card.Body>
                                                            <Card.Title>Sobre Este Riesgo</Card.Title>
                                                            <Card.Text>
                                                                <p>Este riesgo aparece en ....</p>
                                                                <p>Este riesgo aparece en ....</p>
                                                                <p>Este riesgo aparece en ....</p>
                                                            </Card.Text>
                                                        </Card.Body>
                                                    </Card>
                                                </Col>
                                                <Col>
                                                    <Card>
                                                        <Card.Body>
                                                            <Card.Title class="text-center">
                                                                <h2>Información Específica</h2>
                                                            </Card.Title>
                                                            <Table striped bordered hover responsive="md">
                                                                <tbody>
                                                                    <tr><td><b>Tipo General</b></td><td>{this.state.risk.generalType}</td></tr>
                                                                    <tr><td><b>Tipo Por Área</b></td><td>{this.state.risk.areaType}</td></tr>
                                                                    <tr><td><b>Tipo Específico</b></td><td>{this.state.risk.specType}</td></tr>
                                                                    <tr><td><b>Probabilidad</b></td><td>{this.state.risk.probability}</td></tr>
                                                                    <tr><td><b>Impacto</b></td><td>{this.state.risk.impact}</td></tr>
                                                                    <tr><td><b>Magnitud</b></td><td>{this.state.risk.magnitude}</td></tr>
                                                                </tbody>
                                                            </Table>
                                                        </Card.Body>
                                                    </Card>
                                                </Col>
                                            </Row>
                                        </Container>
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

