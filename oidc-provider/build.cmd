@echo off
rd /s /q target
grails -verbose maven-install
