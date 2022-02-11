import React, { Component } from 'react';
import './TopButtons.css';
import EditPlanModal from './EditPlanModal';
import { Stack, Button, OverlayTrigger, Tooltip } from "react-bootstrap";
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';
//import { toast } from 'react-toastify';

class TopButtons extends Component {

    constructor(props) {
        super(props);
        this.state = {
            show: false,
            showDel: false
        };
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.openModalDelete = this.openModalDelete.bind(this);
        this.closeModalDelete = this.closeModalDelete.bind(this);
    }

    openModal = () => {
        this.setState({ show: true });
    };

    closeModal = () => {
        this.setState({ show: false });
    };

    openModalDelete = () => {
        this.setState({ showDel: true });
    };

    closeModalDelete = () => {
        this.setState({ showDel: false });
    };

    render() {
        let statusClass = "";
        let name = this.props.name;
        let type = this.props.type;
        let id = this.props.id;
        let authorName = this.props.authorName;
        let description = this.props.description;
        //let entryDate = this.props.entryDate;
        //let riskList = this.props.riskList;
        let refreshPage = this.props.refreshPage;
        switch (this.props.status) {
            case 'Activo':
                statusClass = 'in-progress';
                break;
            case 'Inactivo':
                statusClass = 'no-progress';
                break;
            case 'Completo':
                statusClass = 'completed';
                break;
            default:
                statusClass = 'unknown';
                break;
        }
        return (
            <div>
                {/* Mobile */}
                <div className='d-lg-none'>
                    <Stack className="mt-4" direction="horizontal" gap={3}>
                        {/* Editar */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Editar Plan
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant="outline-primary" onClick={this.openModal} >
                                <h2><i className="bi bi-pencil-square"></i></h2>
                            </Button>
                        </OverlayTrigger>

                        {/* Eliminar */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Eliminar Plan
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"} onClick={this.openModalDelete} disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>
                                <h2><i className="bi bi-trash"></i></h2>
                            </Button>
                        </OverlayTrigger>

                        {/* Agregar Involucrado */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Agregar Involucrado
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant="outline-success">
                                <h2><i className="bi bi-person-plus"></i></h2>
                            </Button>
                        </OverlayTrigger>

                        {/* Matriz de Riesgos */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Matriz de Riesgos
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant="outline-warning">
                                <h2><i className="bi bi-clipboard-data"></i></h2>
                            </Button>
                        </OverlayTrigger>
                        <Button variant="light">
                            <h2><i className="bi bi-download"></i></h2>
                        </Button>
                    </Stack>
                </div>
                {/* PC */}
                <div className="d-none d-lg-block">
                    <Stack className="mt-4" direction="horizontal" gap={3}>
                        <Button variant="link" href="#/planes">
                            <h5><i className="bi bi-chevron-left"></i> Volver</h5>
                        </Button>
                        {/* Editar */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Editar Plan
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant="outline-primary" onClick={this.openModal} >
                                <h2><i className="bi bi-pencil-square"></i></h2>
                            </Button>
                        </OverlayTrigger>

                        {/* Eliminar */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Eliminar Plan
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"} onClick={this.openModalDelete} disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>
                                <h2><i className="bi bi-trash"></i></h2>
                            </Button>
                        </OverlayTrigger>

                        {/* Agregar Involucrado */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Agregar Involucrado
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant="outline-success">
                                <h2><i className="bi bi-person-plus"></i></h2>
                            </Button>
                        </OverlayTrigger>

                        {/* Matriz de Riesgos */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Matriz de Riesgos
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant="outline-warning">
                                <h2><i className="bi bi-clipboard-data"></i></h2>
                            </Button>
                        </OverlayTrigger>


                        <Button className="ms-auto" variant="light">
                            <h2><i className="bi bi-download"></i></h2>
                        </Button>{' '}
                        <div className="vr" />
                        <Button className={statusClass} variant="success">{this.props.status}</Button>{' '}
                    </Stack>
                </div>
                <EditPlanModal
                    name={name}
                    type={type}
                    id={id}
                    authorName={authorName}
                    description={description}
                    status={this.props.status}
                    entryDate={this.props.entryDate}
                    riskList={this.props.riskList}
                    show={this.state.show}
                    closeModal={this.closeModal}
                    refreshPage={refreshPage} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelete}
                    action={this.props.deletePlan}
                    header={"Eliminar Plan"}
                    body={"¿Desea eliminar este plan? Una vez eliminado no se podra recuperar el plan seleccionado"} />
            </div>
        );
    }
};
export default TopButtons;
