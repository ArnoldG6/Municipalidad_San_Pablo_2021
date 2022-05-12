import React, { Component } from 'react';
import './TopButtons.css';
import { Stack, Button, OverlayTrigger, Tooltip } from "react-bootstrap";

class TopButtons extends Component {
    render() {
        let statusClass = this.props.status;
        switch (statusClass) {
            case 'Activo':
                statusClass = 'success';
                break;
            case 'Inactivo':
                statusClass = 'danger';
                break;
            case 'Completo':
                statusClass = 'primary';
                break;
            default:
                statusClass = 'secondary';
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
                            <Button
                                variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "outline-primary" : "outline-dark"}
                                disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                                onClick={this.props.openModalEdit} >
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
                            <Button
                                variant={this.props.permsCheck("SUPER_ADMIN") ? "outline-danger" : "outline-dark"}
                                disabled={!this.props.permsCheck("SUPER_ADMIN") ? true : false}
                                onClick={this.props.openModalDelete}>
                                <h2><i className="bi bi-trash"></i></h2>
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
                            <Button variant="outline-warning" onClick={this.props.handleRiskTableButtonClick}>
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
                            <Button
                                variant={this.props.permsCheck("SUPER_ADMIN") || this.props.permsCheck("ADMIN") || this.props.permsCheck("INVOLVED") ? "outline-primary" : "outline-dark"}
                                disabled={!this.props.permsCheck("SUPER_ADMIN") && !this.props.permsCheck("ADMIN") && !this.props.permsCheck("INVOLVED") ? true : false}
                                onClick={this.props.openModalEdit} >
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
                            <Button
                                variant={this.props.permsCheck("SUPER_ADMIN") ? "outline-danger" : "outline-dark"}
                                disabled={!this.props.permsCheck("SUPER_ADMIN") ? true : false}
                                onClick={this.props.openModalDelete}>
                                <h2><i className="bi bi-trash"></i></h2>
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
                            <Button variant="outline-warning" onClick={this.props.handleRiskTableButtonClick}>
                                <h2><i className="bi bi-clipboard-data"></i></h2>
                            </Button>
                        </OverlayTrigger>


                        <Button className="ms-auto" variant="light">
                            <h2><i className="bi bi-download"></i></h2>
                        </Button>{' '}
                        <div className="vr" />
                        <Button variant={statusClass}>{this.props.status}</Button>{' '}
                    </Stack>
                </div>
            </div>
        );
    }
};
export default TopButtons;
