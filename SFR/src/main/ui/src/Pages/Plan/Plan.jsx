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
            riskList: []
        };
        this.tableAssign = this.tableAssign.bind(this);
        this.tableHandler = this.tableHandler.bind(this);
        this.removeRisks = this.removeRisks.bind(this);
    }

    componentDidMount() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: "http://localhost:8080/SFR/API/RetrievePlan",
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
                return <RiskTable riesgos={this.state.riskList} removeRisks={this.removeRisks}/>;
            case "incidents":
                return <h1>Incidentes Aqui</h1>;
            case "involved":
                return <h1>Involucrados Aqui</h1>;
            default:
                return <h1>Error</h1>;
        }
    }

    removeRisks(idRisk) {
        let options = {
            url: "http://localhost:8080/SFR/API/RetrievePlan",
            method: "DELETE",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.id,
                'riskID': idRisk
            }
        }
        axios(options)
            .then(response => {
                this.props.history.push('/plan?id=' + this.state.id);
            }).catch(error => {
                toast.error("Error al remover el riesgo seleccionado.", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 10000
                });
            });
    }

    render() {
        let tableData = this.tableAssign();
        return (
            <div className="Plan-Container">
                {/* Comentarios del Plan */}
                <CommentSideBar />

                {/* Contenedor para el resto de la pagina */}
                <div className="container-fluid Data-Container">

                    {/* Botones de uso en el Plan */}
                    <Row>
                        <TopButtons name={this.state.name} type={this.state.type} id={this.state.id}
                        authorName={this.state.authorName} description={this.state.description} status={this.state.status} />
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
                    <ToastContainer />
                </div>
            </div>
        );
    }
}
export default Plan;