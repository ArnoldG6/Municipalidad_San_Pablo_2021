import React, { Component } from 'react';
import { Nav } from "react-bootstrap";
import './CommentSideBar.css';

class CommentSideBar extends Component {
    render() {
        return (
            <div className="plan-sidebar">
                <h2 className="text-center mt-3">Comentarios</h2>
                <Nav className="navbar navbar-default" role="navigation">
                    <div class="side-menu-container">
                        <ul class="nav nav-pills flex-column mb-auto">
                            
                        </ul>
                    </div>
                </Nav>
            </div>
        );
    }
};
export default CommentSideBar;
