<!DOCTYPE html>
<html lang="en" style="height: 100%">
<head>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.1.0/mdb.min.css" rel="stylesheet"/>
    <style>
        .figure-caption {
            padding: 10px;
        }

        .carousel-item {
            transform: scale(1);
        }
    </style>
</head>
<body style="height: 100%;">
<div class="container-fluid bg-dark" style="height: 100%;">
    <div class="row text-center" style="height: 100%; display: flex; align-items: center;">
        <#include "previous.ftl">
        <div class="col">
            <div class="row">
                <div class="row" style="height: 30px;display: flex;justify-content: center; margin-top: 20px">
                    <div class="range" style="width: 50%">
                        <input type="range" class="form-range" min="0" max="2" step="0.1" id="${id}-slider">
                    </div>
                </div>
                <div class="row">
                    <div id="${id}" class="carousel slide carousel-fade">
                        <div class="carousel-inner">
                            <#include "actual.ftl">
                            <#if reference??>
                                <#include "reference.ftl">
                                <#include "diff.ftl">
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <#include "next.ftl">
    </div>
</div>
<#include "update-popup.ftl">
<#include "scripts.ftl">
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/4.1.0/mdb.min.js"></script>
</body>
</html>