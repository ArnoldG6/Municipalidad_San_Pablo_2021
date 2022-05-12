import React, { Component } from 'react';
import './Plan.css'
import { Row, Col, Card, Nav, Container } from "react-bootstrap";
import CommentSideBar from './Components/CommentSideBar';
import TopButtons from './Components/TopButtons';
import RiskTable from './Components/RiskTable';
import IncidentTable from './Components/IncidentTable';
import InvolvedTable from './Components/InvolvedTable';
import EditPlanModal from './Components/EditPlanModal';
import GenericModal from '../../SharedComponents/GenericModal/GenericModal';
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';
import Cookies from 'universal-cookie';
import fileDownload from 'js-file-download';
import FileSaver from 'file-saver';
import JsFileDownloader from 'js-file-downloader';
const cookies = new Cookies();

class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            table: "risks",
            sortingValue: 'date',
            sortingWay: 'description',
            plan: null,
            availableRisks: [],
            availableUsers: [],
            showEdit: false,
            showDel: false
        };
        this.tableAssign = this.tableAssign.bind(this);
        this.tableHandler = this.tableHandler.bind(this);
        this.removeRisks = this.removeRisks.bind(this);
        this.removeIncidences = this.removeIncidences.bind(this);
        this.addInvolved = this.addInvolved.bind(this);
        this.removeInvolved = this.removeInvolved.bind(this);
        this.deletePlan = this.deletePlan.bind(this);
        this.refreshPage = this.refreshPage.bind(this);
        this.addRisk = this.addRisk.bind(this);
        this.retrieveTypes = this.retrieveTypes.bind(this);
        this.retrieveRemainingRisks = this.retrieveRemainingRisks.bind(this);
        this.retrieveUsers = this.retrieveUsers.bind(this);
        this.openModalEdit = this.openModalEdit.bind(this);
        this.closeModalEdit = this.closeModalEdit.bind(this);
        this.openModalDelete = this.openModalDelete.bind(this);
        this.closeModalDelete = this.closeModalDelete.bind(this);
        this.permsCheck = this.permsCheck.bind(this);
        this.removeComment = this.removeComment.bind(this);
        this.handleRiskTableButtonClick = this.handleRiskTableButtonClick.bind(this);
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

    permsCheck(toCheck) {
        let perm = false;
        if (typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) !== 'undefined'
            && typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) !== 'undefined'
            && this.state.plan !== null) {
            if (toCheck === "INVOLVED") {
                var id = cookies.get('username', { path: process.env.REACT_APP_AUTH });
                this.state.plan.involvedList.map((involved) => {
                    console.log(id)
                    console.log(involved.idUser)
                    if (involved.idUser.toString() === id) {
                        perm = true;
                        return true;
                    }
                    return false;
                })
            }
            else {
                cookies.get('roles', { path: process.env.REACT_APP_AUTH }).map((rol) => {
                    if (!(this.state.plan.status === "Completo")) {
                        if (rol.description === toCheck) {
                            perm = true;
                            return true;
                        }
                    }
                    return false;
                })
            }
        }
        return perm;
    }

    async refreshPage() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/PlanServlet/Retrieve/Plan",
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
                    if (this.state.plan === null || typeof this.state.plan === 'undefined') {
                        this.props.history.push('/planes');
                    } else {
                        console.log(this.state.plan)
                        this.retrieveRemainingRisks();
                    }
                });
            }).catch(error => {
                this.props.history.push('/planes');
            });
    }

    retrieveRemainingRisks() {
        let query = new URLSearchParams(this.props.location.search);

        let options2 = {
            url: process.env.REACT_APP_SFR_API_URL + "/PlanServlet/Retrieve/Plan/RemainingRisks",
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
            url: process.env.REACT_APP_SFR_API_URL + `/PlanServlet/Retrieve/PlanTypes`,
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
                }, () => {
                    this.retrieveUsers();
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

    retrieveUsers() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: process.env.REACT_APP_SFR_API_URL + `/PlanServlet/Retrieve/Plan/RemainingUsers`,
            method: 'POST',
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
                    availableUsers: response.data
                }, () => {
                });
            }).catch(error => {
                toast.error("Error recuperando los usuarios de Planes", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });
    }

    handleRiskTableButtonClick() {
        var url = process.env.REACT_APP_SFR_API_URL + "/PlanServlet/RiskTable?planID=" + this.state.plan.pkID + "&userID=" + cookies.get('username', { path: process.env.REACT_APP_AUTH })

        var date = new Date();

        var filename = "Matriz_de_riesgos_" + this.state.plan.id + "_" + date.toLocaleDateString("es-ES") + "_" + date.toLocaleTimeString() + ".pdf";

        new JsFileDownloader({
            url: url,
            filename: filename
        }).catch(error => {
            toast.error("Error al obtener la matriz de riesgos", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 5000
            });
        })
    }

    tableHandler(table) {
        this.setState({ "table": table })
    }


    tableAssign() {
        if (this.state.plan !== null) {
            switch (this.state.table) {
                case "risks":
                    return <RiskTable permsCheck={this.permsCheck} riesgos={this.state.plan.riskList} removeRisks={this.removeRisks} addRisk={this.addRisk} availableRisks={this.state.availableRisks} />;
                case "incidents":
                    return <IncidentTable permsCheck={this.permsCheck} refreshPage={this.refreshPage} planID={this.state.plan.pkID} incidentes={this.state.plan.incidenceList} removeIncidences={this.removeIncidences} riesgos={this.state.plan.riskList} />;
                case "involved":
                    return <InvolvedTable permsCheck={this.permsCheck} involved={this.state.plan.involvedList} removeInvolved={this.removeInvolved} addInvolved={this.addInvolved} planID={this.state.plan.id} users={this.state.availableUsers} />;
                default:
                    return <h1>Error</h1>;
            }
        } else {
            return <h1>Error cargando los datos del Plan</h1>;
        }
    }

    addRisk(risksID) {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + `/PlanManager/Insert/Risk`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planPKID': this.state.plan.pkID,
                'riskIDs': risksID,
                'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
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
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema insertando los Riesgos solicitados.";
                            break;
                        case 401:
                            msj = "Este usuario no cuenta con permisos para insertar Riesgos a este Plan.";
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

    removeRisks(idRisk) {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/PlanManager/Delete/Risk",
            method: "DELETE",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planPkID': this.state.plan.pkID,
                'riskPkID': idRisk,
                'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
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
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema encontrando los datos necesarios para eliminar el Riesgo.";
                            break;
                        case 401:
                            msj = "Este usuario no cuenta con permisos para eliminar Riesgos de este Plan.";
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

    removeComment(idComment) {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/PlanManager/Delete/Comment",
            method: "DELETE",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planPkID': this.state.plan.pkID,
                'commentID': idComment,
                'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
            }
        }
        axios(options)
            .then(response => {
                this.refreshPage();
                toast.success("El comentario fue eliminado correctamente!", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema eliminando el Comentario solicitado.";
                            break;
                        case 401:
                            msj = "Este usuario no cuenta con permisos para eliminar Comentarios de este Plan.";
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

    removeIncidences(idIncidence) {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/PlanManager/Delete/Incidence",
            method: "DELETE",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planPkID': this.state.plan.pkID,
                'incidencePkID': idIncidence,
                'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
            }
        }
        axios(options)
            .then(response => {
                this.refreshPage();
                toast.success("La incidencia fue eliminada correctamente!", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema encontrando los datos necesarios para eliminar la Incidencia.";
                            break;
                        case 401:
                            msj = "Este usuario no cuenta con permisos para eliminar Incidencias de este Plan.";
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

    addInvolved(userIDs) {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + `/PlanManager/Insert/Involved`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.plan.pkID,
                'userIDs': userIDs,
                'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
            }
        }

        axios(options)
            .then(response => {
                this.refreshPage();
                toast.success("Se agregaron los involucrados correctamente!", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema insertando el/los Involucrado(s) solicitado(s).";
                            break;
                        case 401:
                            msj = "Este usuario no cuenta con permisos para insertar Involucrados a este Plan.";
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

    removeInvolved(userID) {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + "/PlanManager/Delete/Involved",
            method: "DELETE",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': this.state.plan.pkID,
                'involvedID': userID,
                'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
            }
        }
        axios(options)
            .then(response => {
                this.refreshPage();
                toast.success("El involucrado fue eliminado correctamente!", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema eliminando el Involucrado solicitado.";
                            break;
                        case 401:
                            msj = "Este usuario no cuenta con permisos para eliminar Involucrados de este Plan.";
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

    deletePlan() {
        let options = {
            url: process.env.REACT_APP_SFR_API_URL + `/PlanManager/Delete`,
            method: 'DELETE',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'pkID': this.state.plan.pkID,
                'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH })
            }
        }
        axios(options)
            .then(response => {
                this.props.history.push('/planes');
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "Hubo un problema encontrando el Plan que desea eliminar.";
                            break;
                        case 401:
                            msj = "Este usuario no cuenta con permisos para eliminar Planes.";
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
                            status={(this.state.plan === null) ? "Cargando.." : this.state.plan.status}
                            permsCheck={this.permsCheck}
                            planID={(this.state.plan === null) ? null : this.state.plan.id}
                            handleRiskTableButtonClick={this.handleRiskTableButtonClick}
                        />
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
                    <CommentSideBar refreshPage={this.refreshPage} removeComment={this.removeComment} plan={this.state.plan} permsCheck={this.permsCheck} mobile={true} />
                </div>
                {/* PC */}
                <Container fluid className="d-none d-lg-block">
                    <Row>
                        <Col lg="3" className='Comment-Container overflow-y-scroll'>
                            {/* Comentarios del Plan */}
                            <CommentSideBar refreshPage={this.refreshPage} removeComment={this.removeComment} plan={this.state.plan} permsCheck={this.permsCheck} mobile={false} />
                        </Col>
                        <Col>
                            {/* Contenedor para el resto de la pagina */}
                            <div className="container-fluid">

                                {/* Botones de uso en el Plan */}
                                <Row>
                                    <TopButtons
                                        openModalEdit={this.openModalEdit}
                                        openModalDelete={this.openModalDelete}
                                        status={(this.state.plan === null) ? "Cargando.." : this.state.plan.status}
                                        permsCheck={this.permsCheck}
                                        planID={(this.state.plan === null) ? null : this.state.plan.id}
                                        handleRiskTableButtonClick={this.handleRiskTableButtonClick} />
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
                        </Col>
                    </Row>
                </Container>
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
