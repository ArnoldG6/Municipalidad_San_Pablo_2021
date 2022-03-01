import React, { Component } from 'react';
import './Plan.css'
import { Row, Card, Nav } from "react-bootstrap";
import CommentSideBar from './Components/CommentSideBar';
import TopButtons from './Components/TopButtons';
import RiskTable from './Components/RiskTable';
import EditPlanModal from './Components/EditPlanModal';
import GenericModal from '../../SharedComponents/GenericModal/GenericModal';
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';
//import Cookies from 'universal-cookie';
//const cookies = new Cookies();

class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            table: "risks",
            plan: null,
            availableRisks: [],
            showEdit: false,
            showDel: false
        };
        this.tableAssign = this.tableAssign.bind(this);
        this.tableHandler = this.tableHandler.bind(this);
        this.removeRisks = this.removeRisks.bind(this);
        this.deletePlan = this.deletePlan.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
        this.addRisk = this.addRisk.bind(this);
        this.retrieveTypes = this.retrieveTypes.bind(this);
        this.retrieveRemainingRisks = this.retrieveRemainingRisks.bind(this);
        this.openModalEdit = this.openModalEdit.bind(this);
        this.closeModalEdit = this.closeModalEdit.bind(this);
        this.openModalDelete = this.openModalDelete.bind(this);
        this.closeModalDelete = this.closeModalDelete.bind(this);
    }

    componentDidMount() {
        this.refreshPage();
    }

    refreshPage() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_API_URL + "/PlanServlet/Retrieve/Plan",
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
                this.setState({
                    plan: response.data
                }, () => {
                    this.retrieveRemainingRisks();
                });
            }).catch(error => {
                this.props.history.push('/planes');
            });
    }

    retrieveRemainingRisks() {
        let query = new URLSearchParams(this.props.location.search);

        let options2 = {
            url: process.env.REACT_APP_API_URL + "/PlanServlet/Retrieve/Plan/RemainingRisks",
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
            this.setState({
                availableRisks: response.data
            }, () => {
                this.retrieveTypes();
            })
        }).catch((error) => {
            console.error(error.message);
        });
    }

    retrieveTypes() {
        let options = {
            url: process.env.REACT_APP_API_URL + `/PlanServlet/Retrieve/PlanTypes`,
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
                toast.error("Error recuperando los tipos/subtipos de Planes", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });
    }

    tableHandler(table) {
        this.setState({ "table": table })
    }

    tableAssign() {
        if (this.state.plan !== null) {
            switch (this.state.table) {
                case "risks":
                    return <RiskTable riesgos={this.state.plan.riskList} removeRisks={this.removeRisks} addRisk={this.addRisk} availableRisks={this.state.availableRisks} />;
                case "incidents":
                    return <h1>Incidentes Aqui</h1>;
                case "involved":
                    return <h1>Involucrados Aqui</h1>;
                default:
                    return <h1>Error</h1>;
            }
        } else {
            return <h1>Error cargando los datos del Plan</h1>;
        }
    }

    addRisk(risksID) {
        let options = {
            url: process.env.REACT_APP_API_URL + `/PlanManager/Insert/Risk`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.plan.id,
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
            url: process.env.REACT_APP_API_URL + "/PlanManager/Delete/Risk",
            method: "DELETE",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.plan.id,
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
            url: process.env.REACT_APP_API_URL + `/PlanManager/Delete`,
            method: 'DELETE',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'id': this.state.plan.id
            }
        }
        axios(options)
            .then(response => {
                this.props.history.push('/planes');
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

    render() {
        let tableData = this.tableAssign();
        return (
            <div className="Plan-Container">
                {/* Mobile */}
                <div className='d-lg-none container-fluid'>
                    <Row>
                        <TopButtons
                            openModalEdit={this.openModalEdit}
                            openModalDelete={this.openModalDelete}
                            status={(this.state.plan === null) ? "Cargando.." : this.state.plan.status} />
                    </Row>
                    {/* Datos del Plan */}
                    <Row className="mt-4">
                        {
                            (this.state.plan === null || typeof this.state.plan === 'undefined') ?
                                <h1>Cargando Datos</h1> :
                                <div>
                                    <h1>{this.state.plan.name}</h1>
                                    <h2>{this.state.plan.id}</h2>
                                    <h2>{this.state.plan.type} - {this.state.plan.subtype}</h2>
                                    <h2>Estado: {this.state.plan.status}</h2>
                                    <h4>{this.state.plan.entryDate}</h4>
                                    <h4>{this.state.plan.authorName}</h4>
                                    <p>{this.state.plan.description}</p>
                                </div>
                        }
                    </Row>
                    <Card id="card">
                        <Card.Header>
                            <Nav fill variant="tabs" defaultActiveKey="riesgosTab">
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
                                openModalEdit={this.openModalEdit}
                                openModalDelete={this.openModalDelete}
                                status={(this.state.plan === null) ? "Cargando.." : this.state.plan.status} />
                        </Row>
                        {/* Datos del Plan */}
                        <Row className="mt-4">
                            {
                                (this.state.plan === null || typeof this.state.plan === 'undefined') ?
                                    <h1>Cargando Datos</h1> :
                                    <div>
                                        <h1>{this.state.plan.name}</h1>
                                        <h2>{this.state.plan.id}</h2>
                                        <h2>{this.state.plan.type} - {this.state.plan.subtype}</h2>
                                        <h4>{this.state.plan.entryDate}</h4>
                                        <h4>{this.state.plan.authorName}</h4>
                                        <p>{this.state.plan.description}</p>
                                    </div>
                            }
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
                <EditPlanModal
                    plan={this.state.plan}
                    show={this.state.showEdit}
                    closeModal={this.closeModalEdit}
                    refreshPage={this.refreshPage}
                    typesMap={this.state.typesMap} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelete}
                    action={this.deletePlan}
                    header={"Eliminar Plan"}
                    body={"¿Desea eliminar este plan? Una vez eliminado no se podra recuperar el plan seleccionado"} />
                <ToastContainer />
            </div>
        );
    }
}
export default Plan;
