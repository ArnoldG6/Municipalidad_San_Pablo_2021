import React, { Component } from 'react';
import './Plan.css'
import { Row, Card, Nav } from "react-bootstrap";
import CommentSideBar from './Components/CommentSideBar';
import TopButtons from './Components/TopButtons';
import RiskTable from './Components/RiskTable';
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';


class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            table: "risks",
            id: "",
            authorName: "",
            name: "",
            description: "",
            entryDate: "",
            status: "",
            type: "",
            riskList: [],
            availableRisks: []
        };
        this.tableAssign = this.tableAssign.bind(this);
        this.tableHandler = this.tableHandler.bind(this);
        this.removeRisks = this.removeRisks.bind(this);
        this.deletePlan = this.deletePlan.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
        this.addRisk = this.addRisk.bind(this);
    }

    componentDidMount() {
        this.refreshPage();
        console.log(this.state.riskList);
        console.log(this.state.availableRisks);
    }

    refreshPage() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_API_URL + "/RetrievePlan",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': query.get('id')
            }
        }
        axios(options)
            .then(response => {
                let plan = response.data;
                this.setState({
                    id: plan.id,
                    authorName: plan.authorName,
                    name: plan.name,
                    description: plan.description,
                    entryDate: plan.entryDate,
                    status: plan.status,
                    type: plan.type,
                    riskList: plan.riskList
                });

                let options2 = {
                    url: process.env.REACT_APP_API_URL + "/PlanManager/getRiskListByPlanNoRep",
                    method: "POST",
                    header: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    data: {
                        'planID': query.get('id')
                    }
                }

                axios(options2).then(response => {
                    this.setState({ availableRisks: response.data })
                }).catch((error) => {
                    console.error(error.message);
                });
            }).catch(error => {
                this.props.history.push('/planes');
            });
    }

    tableHandler(table) {
        this.setState({ "table": table })
    }

    tableAssign() {
        switch (this.state.table) {
            case "risks":
                return <RiskTable riesgos={this.state.riskList} removeRisks={this.removeRisks} addRisk={this.addRisk} availableRisks={this.state.availableRisks} />;
            case "incidents":
                return <h1>Incidentes Aqui</h1>;
            case "involved":
                return <h1>Involucrados Aqui</h1>;
            default:
                return <h1>Error</h1>;
        }
    }

    addRisk(risksID) {
        let options = {
            url: process.env.REACT_APP_API_URL + `/PlanManager/associateRiskToPlan`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.id,
                'riskIDs': risksID
            }
        }

        axios(options)
            .then(response => {
                this.refreshPage();
                toast.success("Se agregaron los riesgos correctamente!", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            }).catch(error => {
                toast.error("Hubo un error agregando los riesgos al plan.", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });
    }

    removeRisks(idRisk) {
        let options = {
            url: process.env.REACT_APP_API_URL + "/PlanManager/deleteRisk",
            method: "DELETE",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.id,
                'riskID': idRisk.toString()
            }
        }
        axios(options)
            .then(response => {
                this.refreshPage();
                toast.success("El riesgo fue eliminado correctamente!", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            }).catch(error => {
                toast.error("Error al remover el riesgo seleccionado.", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });
    }

    deletePlan() {
        let options = {
            url: process.env.REACT_APP_API_URL + `/PlanManager/delete`,
            method: 'DELETE',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'id': this.state.id
            }
        }
        axios(options)
            .then(response => {
                this.props.history.push('/planes');
            })
    }

    render() {
        let tableData = this.tableAssign();
        return (
            <div className="Plan-Container">
                {/* Mobile */}
                <div className='d-lg-none container-fluid'>
                    <Row>
                        <TopButtons
                            name={this.state.name}
                            type={this.state.type}
                            id={this.state.id}
                            authorName={this.state.authorName}
                            description={this.state.description}
                            status={this.state.status}
                            entryDate={this.state.entryDate}
                            riskList={this.state.riskList}
                            refreshPage={this.refreshPage}
                            deletePlan={this.deletePlan} />
                    </Row>
                    {/* Datos del Plan */}
                    <Row className="mt-4">
                        <h1>{this.state.name}</h1>
                        <h2>{this.state.type}-{this.state.id}</h2>
                        <h2>Estado: {this.state.status}</h2>
                        <h4>{this.state.entryDate}</h4>
                        <h4>{this.state.authorName}</h4>
                        <p>{this.state.description}</p>
                    </Row>
                    <Card id="card">
                        <Card.Header>
                            <Nav justify variant="tabs" defaultActiveKey="riesgosTab">
                                <Nav.Item>
                                    <Nav.Link eventKey="riesgosTab" onClick={() => { this.tableHandler("risks") }}>Riesgos</Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link eventKey="incidenciasTab" onClick={() => { this.tableHandler("incidents") }}>Incidencias</Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link eventKey="involucradosTab" onClick={() => { this.tableHandler("involved") }}>Involucrados</Nav.Link>
                                </Nav.Item>
                            </Nav>
                        </Card.Header>
                        <Card.Body>
                            {tableData}
                        </Card.Body>
                    </Card>
                </div>
                {/* PC */}
                <div className="d-none d-lg-block">
                    {/* Comentarios del Plan */}
                    <CommentSideBar />

                    {/* Contenedor para el resto de la pagina */}
                    <div className="container-fluid Data-Container">

                        {/* Botones de uso en el Plan */}
                        <Row>
                            <TopButtons
                                name={this.state.name}
                                type={this.state.type}
                                id={this.state.id}
                                authorName={this.state.authorName}
                                description={this.state.description}
                                status={this.state.status}
                                entryDate={this.state.entryDate}
                                riskList={this.state.riskList}
                                refreshPage={this.refreshPage}
                                deletePlan={this.deletePlan} />
                        </Row>

                        {/* Datos del Plan */}
                        <Row className="mt-4">
                            <h1>{this.state.name}</h1>
                            <h2>{this.state.type}-{this.state.id}</h2>
                            <h4>{this.state.entryDate}</h4>
                            <h4>{this.state.authorName}</h4>
                            <p>{this.state.description}</p>
                        </Row>

                        {/* Listas de Datos del Plan */}
                        <Card id="card">
                            <Card.Header>
                                <Nav justify variant="tabs" defaultActiveKey="riesgosTab">
                                    <Nav.Item>
                                        <Nav.Link eventKey="riesgosTab" onClick={() => { this.tableHandler("risks") }}>Riesgos</Nav.Link>
                                    </Nav.Item>
                                    <Nav.Item>
                                        <Nav.Link eventKey="incidenciasTab" onClick={() => { this.tableHandler("incidents") }}>Incidencias</Nav.Link>
                                    </Nav.Item>
                                    <Nav.Item>
                                        <Nav.Link eventKey="involucradosTab" onClick={() => { this.tableHandler("involved") }}>Involucrados</Nav.Link>
                                    </Nav.Item>
                                </Nav>
                            </Card.Header>
                            <Card.Body>
                                {tableData}
                            </Card.Body>
                        </Card>
                    </div>
                </div>
                <ToastContainer />
            </div>
        );
    }
}
export default Plan;
