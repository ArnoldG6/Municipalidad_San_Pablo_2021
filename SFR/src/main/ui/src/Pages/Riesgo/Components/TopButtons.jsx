import React, { Component } from 'react';
import './TopButtons.css';
import { Stack, Button, OverlayTrigger, Tooltip } from "react-bootstrap";
//import { toast } from 'react-toastify';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

class TopButtons extends Component {
    constructor(props) {
        super(props);
        this.checkPermissions = this.checkPermissions.bind(this);
    }

    checkPermissions(toCheck) {
        let perm = false;
        cookies.get('roles', { path: process.env.REACT_APP_AUTH }).map((rol) => {
            if (rol.description === toCheck) {
                perm = true;
                return true;
            }
            return false;
        })
        return perm;
    }

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
                                    Editar Riesgo
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant="outline-primary" onClick={this.props.openModalEdit} >
                                <h2><i className="bi bi-pencil-square"></i></h2>
                            </Button>
                        </OverlayTrigger>

                        {/* Eliminar */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Eliminar Riesgo
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant={this.checkPermissions("SUPER_ADMIN") ? "outline-danger" : "outline-dark"} onClick={this.props.openModalDelete} disabled={this.checkPermissions("SUPER_ADMIN") ? false : true}>
                                <h2><i className="bi bi-trash"></i></h2>
                            </Button>
                        </OverlayTrigger>
                    </Stack>
                </div>
                {/* PC */}
                <div className="d-none d-lg-block">
                    <Stack className="mt-4" direction="horizontal" gap={3}>
                        <Button variant="link" href="#/riesgos">
                            <h5><i className="bi bi-chevron-left"></i> Volver</h5>
                        </Button>
                        {/* Editar */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Editar Riesgo
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant="outline-primary" onClick={this.props.openModalEdit} >
                                <h2><i className="bi bi-pencil-square"></i></h2>
                            </Button>
                        </OverlayTrigger>

                        {/* Eliminar */}
                        <OverlayTrigger
                            delay={{ hide: 450, show: 300 }}
                            overlay={(props) => (
                                <Tooltip {...props}>
                                    Eliminar Riesgo
                                </Tooltip>
                            )}
                            placement="bottom"
                        >
                            <Button variant={this.checkPermissions("SUPER_ADMIN") ? "outline-danger" : "outline-dark"} onClick={this.props.openModalDelete} disabled={this.checkPermissions("SUPER_ADMIN") ? false : true}>
                                <h2><i className="bi bi-trash"></i></h2>
                            </Button>
                        </OverlayTrigger>
                    </Stack>
                </div>
            </div>
        );
    }
};
export default TopButtons;
