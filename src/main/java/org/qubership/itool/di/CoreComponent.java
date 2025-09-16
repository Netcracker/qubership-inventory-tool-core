/*
 * Copyright 2024-2025 NetCracker Technology Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.qubership.itool.di;

import org.qubership.itool.modules.diagram.DiagramService;
import org.qubership.itool.modules.graph.GraphManager;
import org.qubership.itool.modules.graph.GraphService;
import org.qubership.itool.modules.template.TemplateService;
import org.qubership.itool.modules.processor.GraphMerger;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CoreModule.class})
public interface CoreComponent {
    GraphService graphService();
    DiagramService diagramService();
    TemplateService templateService();
    GraphManager graphManager();
    GraphMerger graphMerger();
} 